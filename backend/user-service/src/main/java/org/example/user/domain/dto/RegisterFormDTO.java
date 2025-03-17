package org.example.user.domain.dto;

import lombok.Data;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
@Data
public class RegisterFormDTO {
    private String username;
    private String email;
    private String password;
    private String verifyCode;
}
