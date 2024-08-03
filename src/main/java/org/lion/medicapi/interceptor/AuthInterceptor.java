package org.lion.medicapi.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lion.medicapi.exception.ApiException;
import org.lion.medicapi.exception.ErrorType;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("userId") == null) {
            throw new ApiException(ErrorType.SESSION_NOT_FOUND);
        }
        return true;
    }
}