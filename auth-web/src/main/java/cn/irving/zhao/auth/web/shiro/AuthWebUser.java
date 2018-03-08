package cn.irving.zhao.auth.web.shiro;

import cn.irving.zhao.auth.service.entity.UserBaseInfo;
import cn.irving.zhao.platform.core.shiro.user.ShiroUser;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Irving
 * @version AuthWebUser.java, v 0.1 2018/2/28
 */
@AllArgsConstructor
public class AuthWebUser implements ShiroUser {

    public static final String DEFAULT_USER_ROLE = "user";

    private final String userId;
    private final String password;
    private final String salt;

    private UserBaseInfo userBaseInfo;

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getSalt() {
        return salt;
    }

    @Override
    public Collection<String> getRoles() {
        return Collections.singletonList(DEFAULT_USER_ROLE);
    }

    @Override
    public Collection<String> getPerms() {
        return Collections.emptyList();
    }

    public UserBaseInfo getUserBaseInfo() {
        return userBaseInfo;
    }

    public AuthWebUser setUserBaseInfo(UserBaseInfo userBaseInfo) {
        this.userBaseInfo = userBaseInfo;
        return this;
    }
}
