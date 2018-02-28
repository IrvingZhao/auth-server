package cn.irving.zhao.auth.oauth.manager;

/**
 * @author Irving
 * @version AccessTokenManager.java, v 0.1 2018/2/24
 */
public interface AccessTokenManager {

    long TOKEN_EXPIRE_TIME = 2 * 60 * 60;//TOKEN 过期事件 两小时

    /**
     * 使用clientId,objectId生成Token
     *
     * @param objectId 对象id
     */
    String generateToken(String objectId);

    /**
     * 验证token
     *
     * @return true 验证通过，false token失效
     */
    Boolean validToken(String token);

    /**
     * 根据token获得对应的objectId
     */
    String getObjectIdByToken(String token);

    /**
     * 移除一个token
     */
    void dropAccessToken(String token);

}
