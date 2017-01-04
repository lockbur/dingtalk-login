package com.lockbur.dingtalk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.lockbur.dingtalk.service.DingtalkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

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


    @Resource
    private DingtalkService dingtalkService;

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
        String URL = "https://oapi.dingtalk.com/connect/qrconnect?appid=%s&response_type=code&scope=snsapi_login&redirect_uri=%s";

        final String authorizationUrl = String.format(URL, "dingoaa5dgfhjehkim9czg", "http://jinyinwu.com/ddtalk/callback");

        logger.info("authorizationUrl login {}", authorizationUrl);
        return "redirect:" + authorizationUrl;
    }


    /**
     * 钉钉扫码成功后回调地址
     * @param code
     * @param state
     * @param model
     * @return
     */
    @RequestMapping("/callback")
    public String callBack(String code, String state, Model model) {
        logger.info("callBack code {}", code);
        logger.info("callBack state {}", state);
        String accessToken = dingtalkService.getAccessToken();

        Map<String, String> tmpAuthCodeMaps = dingtalkService.getPersistentCode(accessToken, code);

        String openid = tmpAuthCodeMaps.get("openid");
        String persistentCode = tmpAuthCodeMaps.get("persistent_code");

        logger.info("callBack openid {}", openid);
        logger.info("callBack persistentCode {}", persistentCode);

        String snsToken = dingtalkService.getSNSToken(accessToken, openid, persistentCode);

        String userInfo =dingtalkService.getUserInfo(snsToken);

        logger.info("callBack userInfo {}", userInfo);

        model.addAttribute("userInfo",userInfo);

        return "dashboard";
    }
}
