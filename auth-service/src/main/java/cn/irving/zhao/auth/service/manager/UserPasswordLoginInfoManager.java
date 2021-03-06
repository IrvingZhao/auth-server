package cn.irving.zhao.auth.service.manager;

import cn.irving.zhao.auth.service.entity.UserPasswordLoginInfo;

import javax.security.auth.login.AccountException;

/**
 * @author Irving
 * @version UserPasswordLoginInfoManager.java, v 0.1 2018/2/22
 */
public interface UserPasswordLoginInfoManager {

    /**
     * 根据用户名密码创建新用户
     */
    void addNewUser(String userName, String password) throws AccountException;

    /**
     * 根据用户名，获得用户名密码登陆信息
     */
    UserPasswordLoginInfo getUserPasswordLoginInfoByUserName(String userName);

    /**
     * 根据用户userId，添加一个用户名密码登陆信息
     */
    void addUserPasswordLoginInfo(String userId, String userName, String password);

    /**
     * 添加一个登陆方式
     */
    void addUserPasswordLoginInfo(String userId, String userName);

    /**
     * 根据用户userId、用户名，移除一个用户登陆信息
     */
    void removeUserPasswordLoginInfo(String userId, String userName);

    /**
     * 根据用户userId、用户名，修改登陆密码
     */
    void updateUserPassword(String userId, String userName, String password);

    /**
     * 更改用户所有登陆账户密码
     */
    void updateUserPassword(String userId, String password);

}
