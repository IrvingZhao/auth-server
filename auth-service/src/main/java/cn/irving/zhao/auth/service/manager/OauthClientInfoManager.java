package cn.irving.zhao.auth.service.manager;

/**
 * @author Irving
 * @version OauthClientInfoManager.java, v 0.1 2018/2/22
 */
public interface OauthClientInfoManager {

    CheckStatus checkOauthClient(String clientId, String clientSecurity);

    String updateOauthClientSecurity(String clientId);

    enum CheckStatus {
        SUCCESS,
        NO_CLIENT,
        SIGN_ERROR,
        NO_SIGN;
    }

}
