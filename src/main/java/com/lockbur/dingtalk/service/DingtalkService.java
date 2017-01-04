package com.lockbur.dingtalk.service;

import java.util.Map;
/**
 * Created by wangkun23 on 2017/1/4.
 */
public interface DingtalkService {

    public String getAccessToken();

    public  Map<String, String> getPersistentCode(String accessToken,String code);

    public String getSNSToken(String accessToken, String openid, String persistentCode);

    public String getUserInfo(String snsToken);

}
