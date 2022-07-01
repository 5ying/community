package com.nowcoder.community.controller.interceptor;/*
 *文件名: LoginTicketInterceptor
 *创建者: wwy
 *创建时间:2022/6/21 17:14
 *描述:
 */

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CookieUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = CookieUtil.getValue(request,"ticket");

        if (ticket!=null){
//            查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
//            检查凭证是否有效
            if (loginTicket != null && loginTicket.getStatus()==0 && loginTicket.getExpired().after(new Date())){
//                根据凭证查询用户
                User user = userService.findUserId(loginTicket.getUserId());
//                System.out.println(user.toString());
//                在本次请求中持有的用户
                hostHolder.setUser(user);
            }
        }

//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Method method = handlerMethod.getMethod();
//            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
//            if (loginRequired != null && hostHolder.getUser() == null) {
//                response.sendRedirect(request.getContextPath() + "/login");
//                return false;
//            }
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user!=null && modelAndView !=null){
            modelAndView.addObject("loginUser",user);
//            System.out.println(user.getId());
//            System.out.println(user.getUsername());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
