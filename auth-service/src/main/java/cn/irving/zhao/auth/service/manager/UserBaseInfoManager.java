package cn.irving.zhao.auth.service.manager;

import cn.irving.zhao.auth.service.entity.UserBaseInfo;

/**
 * @author zhaojn1
 * @version UserBaseInfoManager.java, v 0.1 2018/3/8 zhaojn1
 * @project userProfile
 */
public interface UserBaseInfoManager {

    UserBaseInfo getUserBaseInfoById(String userId);

}
