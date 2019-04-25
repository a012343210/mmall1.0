package com.mmall.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Administrator
 * @Date: 2019/4/23 21:27
 * @Description:
 */
public class CookieUtil {
    private static String COOKIE_DOMAIN = ".hsx.com";
    private static String COOKIE_NAME = "hsx_login_token";

    /**
     *
     * 功能描述: 写入cookie
     *
     * @param:
     * @return:
     * @auther: Administrator
     * @date: 2019/4/23 21:33
     */
    public static void writeCookie(HttpServletResponse response, String token){
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setPath("/");//  "/"指根目录 "test"指只有在test及其子域名可以访问到这个cookie
        ck.setMaxAge(60*60*24);//当值为-1时,表示永久存在
        ck.setDomain(COOKIE_DOMAIN);
        response.addCookie(ck);
    }

    /**
     *
     * 功能描述: 获取cookie值
     *
     * @param:
     * @return:
     * @auther: Administrator
     * @date: 2019/4/23 21:37
     */
    public static String getCookie(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void delCookie(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setPath("/");
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setMaxAge(0);
                    response.addCookie(ck);
                }
            }
        }
    }


}