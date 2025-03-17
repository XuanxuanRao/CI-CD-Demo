package org.example.user.controller;

import cn.hutool.core.bean.BeanUtil;
import org.example.api.dto.UserDTO;
import org.example.common.result.Result;
import org.example.common.util.UserContext;
import org.example.user.domain.dto.LoginFormDTO;
import org.example.user.domain.dto.RegisterFormDTO;
import org.example.user.domain.po.User;
import org.example.user.domain.vo.UserLoginVO;
import org.example.user.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody LoginFormDTO loginFormDTO) {
        return Result.success(userService.login(loginFormDTO));
    }

    @GetMapping("/info")
    public Result<UserDTO> getUserInfo() {
        User user = userService.getById(UserContext.getUser());
        if (user == null) {
            return Result.error("System error");
        }

        return Result.success(BeanUtil.copyProperties(user, UserDTO.class));
    }

    @PostMapping("/register")
    public Result<UserLoginVO> register(@RequestBody RegisterFormDTO registerFormDTO) {
        return Result.success(userService.register(registerFormDTO));
    }

    @GetMapping("/{id}")
    public Result<UserDTO> queryUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("User not found");
        }

        return Result.success(BeanUtil.copyProperties(user, UserDTO.class));
    }

}
