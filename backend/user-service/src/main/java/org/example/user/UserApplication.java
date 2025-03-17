package org.example.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
@SpringBootApplication
@MapperScan("org.example.user.mapper")
@EnableFeignClients(basePackages = "org.example.api.client", defaultConfiguration = org.example.api.config.DefaultFeignConfig.class)
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
