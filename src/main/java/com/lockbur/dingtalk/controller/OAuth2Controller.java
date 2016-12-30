package com.lockbur.dingtalk.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户微信静默授权 请求授权地址和回调后获取access_token
 *
 * @author wangkun
 * @version 16/2/29
 */
@Controller
@RequestMapping("/ddtalk")
public class OAuth2Controller {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);


    /**
     * 重构微信登录授权
     *
     * @param redirectUrl
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(String redirectUrl, Model model) {
       // String URL="https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=%s&response_type=code&scope=snsapi_login&redirect_uri=%s";
        String URL="https://oapi.dingtalk.com/connect/qrconnect?appid=%s&response_type=code&scope=snsapi_login&redirect_uri=%s";

        final String authorizationUrl  = String.format(URL,"dingoaa5dgfhjehkim9czg", "http://jinyinwu.com/ddtalk/callback");

        logger.info("authorizationUrl login {}", authorizationUrl);
        return "redirect:" + authorizationUrl;
    }

    /**
     * @param session
     * @param state
     * @param code
     * @param model
     * @return
     * @ 微信回调地址 回调后获取到微信ID 在跳转到注册页面
     * @参照文档
     * @link http://mp.weixin.qq.com/wiki/4/9ac2e7b1f1d22e9e57260f6553822520.html
     * @link 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE
     */
    @RequestMapping("/callback")
    public String callBack(String code, String state, HttpSession session, Model model) {
        logger.debug("callBack code {}", code);
        logger.debug("callBack state {}", state);

        return "";
    }
}
