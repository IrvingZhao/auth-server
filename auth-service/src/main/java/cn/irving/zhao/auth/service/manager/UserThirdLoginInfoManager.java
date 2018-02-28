package cn.irving.zhao.auth.service.manager;

import cn.irving.zhao.auth.service.entity.UserThirdLoginInfo;

/**
 * @author Irving
 * @version UserThirdLoginInfoManager.java, v 0.1 2018/2/22
 */
public interface UserThirdLoginInfoManager {

    UserThirdLoginInfo getThirdLoginInfo(String platform, String thirdId);

    void addThirdLoginInfo(String baseId, String platform, String thirdId);

    void updateThirdLoginInfo(String baseId, String platform, String newThirdId);

    void removeThirdLoginInfo(String baseId, String platform);

}
