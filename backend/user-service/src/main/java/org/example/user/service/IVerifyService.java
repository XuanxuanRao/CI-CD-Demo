package org.example.user.service;

import org.example.common.result.Result;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
public interface IVerifyService {

    Result<Void> sendVerifyCodeForRegister(String email);

    Result<Void> sendVerifyCodeForReset(String email);

}
