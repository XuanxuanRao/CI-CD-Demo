package org.example.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.api.client.EmailClient;
import org.example.api.dto.SendEmailDTO;
import org.example.common.constant.RedisConstant;
import org.example.common.result.Result;
import org.example.common.util.RegexUtil;
import org.example.user.domain.po.User;
import org.example.user.service.IUserService;
import org.example.user.service.IVerifyService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
@Service
@Slf4j
public class VerifyServiceImpl implements IVerifyService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private IUserService userService;

    @Resource
    private EmailClient emailClient;

    @Override
    public Result<Void> sendVerifyCodeForRegister(final String email) {
        // 正则
        if (!RegexUtil.isEmailValid(email)) {
            return Result.error("非法的邮箱地址");
        }

        User user = userService.lambdaQuery().eq(User::getEmail, email).one();
        if (user != null) {
            return Result.error("该邮箱已被注册");
        }

        // 生成验证码
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(RedisConstant.VERIFY_CODE_PREFIX + email, code, RedisConstant.VERIFY_CODE_EXPIRE, TimeUnit.SECONDS);

        // 生成邮件内容
        final String body = loadTemplate("templates/email_register.html")
                .replace("{{verification_code}}", code);

        // 发送邮件
        emailClient.sendEmail(SendEmailDTO.builder()
                    .to(email)
                    .subject("注册验证码")
                    .body(body)
                    .build());

        return Result.success();
    }

    @Override
    public Result<Void> sendVerifyCodeForReset(final String email) {
        User user = userService.lambdaQuery().eq(User::getEmail, email).one();
        if (user == null) {
            return Result.error("该邮箱未注册");
        }

        // 生成验证码
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(RedisConstant.VERIFY_CODE_PREFIX + email, code, RedisConstant.VERIFY_CODE_EXPIRE, TimeUnit.SECONDS);

        // 生成邮件内容
        final String body = loadTemplate("templates/email_reset.html")
                .replace("{{verification_code}}", code)
                .replace("{{username}}", user.getUsername());

        // 发送邮件
        emailClient.sendEmail(SendEmailDTO.builder()
                    .to(email)
                    .subject("重置密码验证码")
                    .body(body)
                    .build());

        return Result.success();
    }


    private String loadTemplate(final String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            log.error("Email template {} not found", fileName);
            throw new RuntimeException("Email template not found");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Error reading email template: " + fileName, e);
        }
    }

}
