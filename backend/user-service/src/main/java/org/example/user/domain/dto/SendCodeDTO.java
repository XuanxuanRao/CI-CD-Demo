package org.example.user.domain.dto;

import lombok.Data;
import org.example.user.enums.SendCodeType;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
@Data
public class SendCodeDTO {
    private String email;
    private SendCodeType type;
}
