package cn.irving.zhao.auth.service.manager;

/**
 * 第三方平台
 *
 * @author zhaojn1
 * @version ThirdPlatformInfoManager.java, v 0.1 2018/3/13 zhaojn1
 * @project userProfile
 */
public interface ThirdPlatformInfoManager {

    String STATE_KEY = "state";

    default String stateKey() {
        return STATE_KEY;
    }

    /**
     * 获取重定向地址
     */
    String redirectUrl(String state);

    /**
     * 根据授权码获取第三方用户id
     */
    String getOpenId(String code);
}
