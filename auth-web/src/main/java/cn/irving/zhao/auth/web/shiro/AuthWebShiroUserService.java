package cn.irving.zhao.auth.web.shiro;

import cn.irving.zhao.auth.service.entity.UserBaseInfo;
import cn.irving.zhao.auth.service.entity.UserPasswordLoginInfo;
import cn.irving.zhao.auth.service.entity.UserThirdLoginInfo;
import cn.irving.zhao.auth.service.manager.UserBaseInfoManager;
import cn.irving.zhao.auth.service.manager.UserPasswordLoginInfoManager;
import cn.irving.zhao.auth.service.manager.UserThirdLoginInfoManager;
import cn.irving.zhao.platform.core.shiro.user.ShiroUser;
import cn.irving.zhao.platform.core.shiro.user.ShiroUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhaojn1
 * @version AuthWebShiroUserService.java, v 0.1 2018/2/28 zhaojn1
 * @project userProfile
 */
@Component
public class AuthWebShiroUserService implements ShiroUserService {

    @Resource
    private UserBaseInfoManager baseInfoManager;

    @Resource
    private UserPasswordLoginInfoManager passwordLoginInfoManager;

    @Resource
    private UserThirdLoginInfoManager thirdLoginInfoManager;

    @Override
    public ShiroUser getUserInfoByUserName(String username) {
        UserPasswordLoginInfo loginInfo = passwordLoginInfoManager.getUserPasswordLoginInfoByUserName(username);
        return new AuthWebUser(loginInfo.getUserId(), loginInfo.getPassword(), loginInfo.getSalt(),
                baseInfoManager.getUserBaseInfoById(loginInfo.getUserId()));
    }

    @Override
    public ShiroUser getUserInfoByPlatformInfo(String platform, String platformUserId) {
        UserThirdLoginInfo loginInfo = thirdLoginInfoManager.getThirdLoginInfo(platform, platformUserId);
        return new AuthWebUser(loginInfo.getUserId(), null, null,
                baseInfoManager.getUserBaseInfoById(loginInfo.getUserId()));
    }
}
