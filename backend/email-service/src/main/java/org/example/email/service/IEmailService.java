package org.example.email.service;

import java.io.File;
import java.util.List;

public interface IEmailService {
    /**
     * 发送简单邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param body 邮件内容
     */
    void sendSimpleEmail(String to, String subject, String body);

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param body 邮件内容
     * @param attachments 附件列表
     */
    void sendEmailWithAttachments(String to, String subject, String body, List<File> attachments);
}
