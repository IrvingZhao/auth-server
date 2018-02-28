package cn.irving.zhao.auth.web.shiro;

import cn.irving.zhao.auth.service.entity.UserPasswordLoginInfo;
import cn.irving.zhao.auth.service.entity.UserThirdLoginInfo;
import cn.irving.zhao.auth.service.manager.UserPasswordLoginInfoManager;
import cn.irving.zhao.auth.service.manager.UserThirdLoginInfoManager;
import cn.irving.zhao.platform.core.shiro.user.ShiroUser;
import cn.irving.zhao.platform.core.shiro.user.ShiroUserService;

import javax.annotation.Resource;

/**
 * @author zhaojn1
 * @version AuthWebShiroUserService.java, v 0.1 2018/2/28 zhaojn1
 * @project userProfile
 */
public class AuthWebShiroUserService implements ShiroUserService {
    @Resource
    private UserPasswordLoginInfoManager passwordLoginInfoManager;

    @Resource
    private UserThirdLoginInfoManager thirdLoginInfoManager;

    @Override
    public ShiroUser getUserInfoByUserName(String username) {
        UserPasswordLoginInfo loginInfo = passwordLoginInfoManager.getUserPasswordLoginInfoByUserName(username);
        return new AuthWebUser(loginInfo.getBaseId(), loginInfo.getPassword(), loginInfo.getSalt());
    }

    @Override
    public ShiroUser getUserInfoByPlatformInfo(String platform, String platformUserId) {
        UserThirdLoginInfo loginInfo = thirdLoginInfoManager.getThirdLoginInfo(platform, platformUserId);
        return new AuthWebUser(loginInfo.getBaseId(), null, null);
    }
}
