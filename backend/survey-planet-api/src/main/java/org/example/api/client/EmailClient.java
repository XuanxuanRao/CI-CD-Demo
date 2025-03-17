package org.example.api.client;

import org.example.api.client.fallback.EmailFallBackFactory;
import org.example.api.dto.SendEmailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
@FeignClient(name = "email-service", fallbackFactory = EmailFallBackFactory.class)
public interface EmailClient {
    @PostMapping("/common/email/send")
    void sendEmail(@RequestBody SendEmailDTO sendEmailDTO);

}
