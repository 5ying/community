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
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.Authenticator;
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

//                构建 用户认证的结果，并存入SecurityContext,以便于Security授权
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user,user.getPassword(),userService.getAuthorities(user.getId()));
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
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
//        之前这句话没有注释掉，出现了逻辑之外的错误：比如 用户明明在登陆状态，但是在页面跳转的时候，security认为用户退出登陆了，
//        又跳转到登陆页面，重新登陆
//        但实际上用户没有退出登陆，是这句代码将用户的信息清理掉了，然后security就认为用户没有权限了（这里的没有权限是指默认用户没有登陆）
//        总而言之，注释掉就好啦
//        SecurityContextHolder.clearContext();
    }
}
