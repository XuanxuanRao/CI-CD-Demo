package org.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.result.Result;
import org.example.user.domain.dto.LoginFormDTO;
import org.example.user.domain.dto.RegisterFormDTO;
import org.example.user.domain.po.User;
import org.example.user.domain.vo.UserLoginVO;

public interface IUserService extends IService<User> {

    UserLoginVO login(LoginFormDTO loginDTO);

    UserLoginVO register(RegisterFormDTO registerDTO);

}
