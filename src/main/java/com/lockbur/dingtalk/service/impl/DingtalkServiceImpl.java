package com.lockbur.dingtalk.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lockbur.dingtalk.service.DingtalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DingtalkServiceImpl implements DingtalkService {


    private static final ConcurrentHashMap<String, String> accessTokenMap = new ConcurrentHashMap();

    //dingtalk初始化参数
    private String dingTalkUrl = "https://oapi.dingtalk.com";
    private String AppID = "dingoacbh0reytzjlrrtwc";
    private String AppSecret = "D8w5DETeOTBFk_wrtLPWDH2uVvuoBsf9cj6vZLdoiAwGRzZZIwhg900x37GS5Cps";
    private String AgentID = "";
    private String CorpID = "";
    private String CorpSecret = "";

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public String getAccessToken() {
        String accessToken = "";
        try {
            //String strUrl = dingTalkUrl + "gettoken?corpid="+CorpID+"&corpsecret="+CorpSecret;
            String strUrl = dingTalkUrl + "/sns/gettoken?appid=" + AppID + "&appsecret=" + AppSecret;

            Map<String, String> retMap = restTemplate.getForObject(strUrl, Map.class);
            accessToken = retMap.get("access_token");

            //SessionHelper.setAccessToken(ses, accessToken);
            accessTokenMap.put("accessToken", accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    @Override
    public Map<String, String> getPersistentCode(String accessToken, String code) {
        try {
            String strUrl = dingTalkUrl + "/sns/get_persistent_code?access_token=" + accessToken;

            Map<String, String> maps = new HashMap<>();
            maps.put("tmp_auth_code", code);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity request = new HttpEntity(maps, headers);

            Map<String, String> response = restTemplate.postForObject(strUrl, request, Map.class);

            accessTokenMap.put("persistentCode", response.get("persistent_code"));

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getSNSToken(String accessToken, String openid, String persistentCode) {
        String snsToken = "";
        try {
            String strUrl = dingTalkUrl + "/sns/get_sns_token?access_token=" + accessToken;
            //3 用户持久授权码去获取snstoken
            Map<String, String> maps = new HashMap<>();
            maps.put("openid", openid);
            maps.put("persistent_code", persistentCode);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity request = new HttpEntity(maps, headers);

            Map<String, String> response = restTemplate.postForObject(strUrl, request, Map.class);

            return response.get("sns_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return snsToken;
    }

    @Override
    public String getUserInfo(String snsToken) {
        String strUrl = dingTalkUrl + "/sns/getuserinfo?sns_token=" + snsToken;
        String response = restTemplate.getForObject(strUrl, String.class);
        return response;
    }

}
