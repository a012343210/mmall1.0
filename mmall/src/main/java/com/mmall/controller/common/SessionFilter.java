package com.mmall.controller.common;

import com.mmall.common.Const;
import com.mmall.common.RedisPool;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtils;
import com.mmall.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Auther: Administrator
 * @Date: 2019/4/24 22:45
 * @Description:
 */
public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String login_token = CookieUtil.getCookie(httpServletRequest);
        if(StringUtils.isNotEmpty(login_token)){
            User user = JsonUtils.String2Object(RedisPoolUtil.get(login_token), User.class);
            if(user != null){
                RedisPoolUtil.expire(login_token, Const.RedisCacheExTime.REDIS_CACHE_EX_TIME);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {

    }
}