package org.example.email.controller;

import org.example.api.dto.SendEmailDTO;
import org.example.common.result.Result;
import org.example.email.service.IEmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
@RestController
@RequestMapping("/common/email")
public class EmailController {

    @Resource
    private IEmailService emailService;

    @PostMapping("/send")
    public Result<Void> sendEmail(@RequestBody SendEmailDTO sendEmailDTO) {
        if (sendEmailDTO.getAttachments() == null || sendEmailDTO.getAttachments().isEmpty()) {
            emailService.sendSimpleEmail(sendEmailDTO.getTo(), sendEmailDTO.getSubject(), sendEmailDTO.getBody());
        } else {
            emailService.sendEmailWithAttachments(sendEmailDTO.getTo(), sendEmailDTO.getSubject(), sendEmailDTO.getBody(), sendEmailDTO.getAttachments());
        }
        return Result.success();
    }
//Anqi test
}
