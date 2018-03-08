package cn.irving.zhao.auth.service.manager.impl;

import cn.irving.zhao.auth.service.entity.UserBaseInfo;
import cn.irving.zhao.auth.service.manager.UserBaseInfoManager;
import cn.irving.zhao.auth.service.mapper.UserBaseInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhaojn1
 * @version UserBaseInfoManagerImpl.java, v 0.1 2018/3/8 zhaojn1
 * @project userProfile
 */
@Service
public class UserBaseInfoManagerImpl implements UserBaseInfoManager {

    @Resource
    private UserBaseInfoMapper baseInfoMapper;

    @Override
    public UserBaseInfo getUserBaseInfoById(String userId) {
        return baseInfoMapper.selectByPrimaryKey(userId);
    }
}
