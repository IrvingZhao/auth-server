package cn.irving.zhao.auth.oauth.manager;

import cn.irving.zhao.auth.oauth.vo.OauthRequestWrapper;

/**
 * @author Irving
 * @version AuthCodeManager.java, v 0.1 2018/2/26
 */
public interface AuthCodeManager {

    long AUTH_CODE_EXPIRE = 10 * 60L;//AuthCode 过期时间 10分钟

    /**
     * 保存oauth请求
     *
     * @param request oauth请求对象
     * @return 请求id
     */
    String saveOauthRequest(OauthRequestWrapper request);

    /**
     * 根据请求id获取请求
     *
     * @param id oauth请求id
     * @return oauth请求对象
     */
    OauthRequestWrapper getOauthRequestById(String id);

    /**
     * 根据请求id删除请求信息
     *
     * @param id 请求id
     */
    void delOauthRequestById(String id);

    /**
     * 根据用户id生成code
     *
     * @param userId 用户id
     * @return 授权code
     */
    String generateCode(String userId);

    /**
     * 根据OauthCode获取token
     */
    String getAccessTokenByCode(String authCode);

}
