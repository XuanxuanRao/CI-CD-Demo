package org.example.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.common.constant.RedisConstant;
import org.example.common.exception.BadRequestException;
import org.example.common.util.RegexUtil;
import org.example.user.config.JwtProperties;
import org.example.user.domain.dto.RegisterFormDTO;
import org.example.user.util.JwtTool;
import org.example.user.domain.dto.LoginFormDTO;
import org.example.user.domain.po.User;
import org.example.user.domain.vo.UserLoginVO;
import org.example.user.mapper.UserMapper;
import org.example.user.service.IUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final PasswordEncoder passwordEncoder;

    private final JwtTool jwtTool;

    private final JwtProperties jwtProperties;

    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public UserLoginVO login(LoginFormDTO loginDTO) {

        User user = lambdaQuery()
                .eq(User::getUsername, loginDTO.getUsername())
                .one();

        if (user == null) {
            throw new RuntimeException("用户名错误");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("用户名或密码错误");
        }

        String token = jwtTool.createToken(user.getId(), jwtProperties.getTokenTTL());
        UserLoginVO userLoginVO = BeanUtil.copyProperties(user, UserLoginVO.class);
        userLoginVO.setToken(token);
        return userLoginVO;
    }

    @Override
    @Transactional
    public UserLoginVO register(RegisterFormDTO registerDTO) {
        if (!RegexUtil.isEmailValid(registerDTO.getEmail())) {
            throw new BadRequestException("非法的邮箱地址");
        }
        if (!RegexUtil.isUsernameValid(registerDTO.getUsername())) {
            throw new BadRequestException("非法的用户名");
        }
        if (!RegexUtil.isPasswordValid(registerDTO.getPassword())) {
            throw new BadRequestException("非法的密码");
        }

        User user = lambdaQuery()
                .eq(User::getUsername, registerDTO.getUsername())
                .one();

        if (user != null) {
            throw new BadRequestException("用户名已存在");
        }

        String correctCode = stringRedisTemplate.opsForValue().get(RedisConstant.VERIFY_CODE_PREFIX + registerDTO.getEmail());
        if (!registerDTO.getVerifyCode().equals(correctCode)) {
            throw new BadRequestException("验证码错误");
        }

        user = BeanUtil.copyProperties(registerDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        if (!save(user)) {
            return null;
        }
        stringRedisTemplate.delete(RedisConstant.VERIFY_CODE_PREFIX + registerDTO.getEmail());

        String token = jwtTool.createToken(user.getId(), jwtProperties.getTokenTTL());
        UserLoginVO userLoginVO = BeanUtil.copyProperties(user, UserLoginVO.class);
        userLoginVO.setToken(token);
        return userLoginVO;
    }
}
