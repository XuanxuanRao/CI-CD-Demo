package org.example.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "org.example.api.client", defaultConfiguration = org.example.api.config.DefaultFeignConfig.class)
public class EmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }

}
