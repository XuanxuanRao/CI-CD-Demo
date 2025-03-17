package org.example.common.interceptors;

import cn.hutool.core.util.StrUtil;
import org.example.common.constant.SystemConstant;
import org.example.common.util.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenxuanrao06@gmail.com
 * @Description:
 */
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userInfo = request.getHeader(SystemConstant.USER_ID_NAME);
        if (StrUtil.isNotBlank(userInfo)) {
            UserContext.setUser(Long.parseLong(userInfo));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
