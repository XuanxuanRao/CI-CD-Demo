package org.example.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.common.result.Result;
import org.example.user.domain.dto.SendCodeDTO;
import org.example.user.enums.SendCodeType;
import org.example.user.service.IVerifyService;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/verify")
public class VerifyController {

    @Resource
    private IVerifyService verifyService;

    @PostMapping
    public Result<Void> sendVerifyCode(@RequestBody SendCodeDTO sendCodeDTO) {
        switch (sendCodeDTO.getType()) {
            case REGISTER:
                return verifyService.sendVerifyCodeForRegister(sendCodeDTO.getEmail());
            case RESET_PASSWORD:
                return verifyService.sendVerifyCodeForReset(sendCodeDTO.getEmail());
            default:
                return Result.error("参数错误");
        }
    }

}
