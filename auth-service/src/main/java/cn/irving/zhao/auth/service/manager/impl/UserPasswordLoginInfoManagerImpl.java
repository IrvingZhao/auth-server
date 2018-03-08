package cn.irving.zhao.auth.service.manager.impl;

import cn.irving.zhao.auth.service.entity.UserBaseInfo;
import cn.irving.zhao.auth.service.entity.UserPasswordLoginInfo;
import cn.irving.zhao.auth.service.manager.UserPasswordLoginInfoManager;
import cn.irving.zhao.auth.service.mapper.UserBaseInfoMapper;
import cn.irving.zhao.auth.service.mapper.UserPasswordLoginInfoMapper;
import cn.irving.zhao.auth.service.util.UUIDUtil;
import cn.irving.zhao.util.base.security.CryptoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.security.auth.login.AccountException;
import java.util.List;

/**
 * @author Irving
 * @version UserPasswordLoginInfoManager.java, v 0.1 2018/2/22
 * @project userProfile
 */
@Service
public class UserPasswordLoginInfoManagerImpl implements UserPasswordLoginInfoManager {

    @Resource
    private UserPasswordLoginInfoMapper loginInfoMapper;

    @Resource
    private UserBaseInfoMapper baseInfoMapper;

    @Override
    public void addNewUser(String userName, String password) throws AccountException {
        UserPasswordLoginInfo loginInfo = getUserPasswordLoginInfoByUserName(userName);
        if (loginInfo != null) {
            throw new AccountException("用户已存在");
        }
        UserBaseInfo baseInfo = new UserBaseInfo(UUIDUtil.generateUUID(), userName);
        String salt = CryptoUtils.getSalt();
        UserPasswordLoginInfo newLoginInfo = new UserPasswordLoginInfo(
                UUIDUtil.generateUUID(),
                userName,
                CryptoUtils.getHash(password, salt),
                salt,
                baseInfo.getId()
        );

        loginInfoMapper.insertSelective(newLoginInfo);
        baseInfoMapper.insertSelective(baseInfo);
        //TODO 添加日志记录
    }

    @Override
    public UserPasswordLoginInfo getUserPasswordLoginInfoByUserName(String userName) {
        Example example = new Example(UserPasswordLoginInfo.class);
        example.createCriteria().andEqualTo("username", userName);
        List<UserPasswordLoginInfo> loginInfo = loginInfoMapper.selectByExample(example);
        return loginInfo == null || loginInfo.isEmpty() ? null : loginInfo.get(0);
    }

    @Override
    public void addUserPasswordLoginInfo(String baseId, String userName, String password) {
        String salt = CryptoUtils.getSalt();
        UserPasswordLoginInfo passwordLoginInfo = new UserPasswordLoginInfo();
        passwordLoginInfo.setUserId(baseId);
        passwordLoginInfo.setUsername(userName);
        passwordLoginInfo.setPassword(CryptoUtils.getHash(password, salt));//加盐加密
        passwordLoginInfo.setSalt(salt);
        passwordLoginInfo.setId(UUIDUtil.generateUUID());
        loginInfoMapper.insertSelective(passwordLoginInfo);
        //TODO 添加日志记录
    }

    @Override
    public void addUserPasswordLoginInfo(String baseId, String userName) {
        Example searchByBaseId = new Example(UserPasswordLoginInfo.class);
        searchByBaseId.createCriteria().andEqualTo("userId", baseId);
        List<UserPasswordLoginInfo> loginInfoList = loginInfoMapper.selectByExample(searchByBaseId);
        if (loginInfoList != null && loginInfoList.size() > 0) {
            UserPasswordLoginInfo tempLoginInfo = loginInfoList.get(0);
            tempLoginInfo.setId(UUIDUtil.generateUUID());
            tempLoginInfo.setUsername(userName);
            loginInfoMapper.insert(tempLoginInfo);
            //TODO 添加日志记录
        }
    }

    @Override
    public void removeUserPasswordLoginInfo(String baseId, String userName) {
        Example example = new Example(UserPasswordLoginInfo.class);
        example.createCriteria().andEqualTo("userId", baseId)
                .andEqualTo("username", userName);
        loginInfoMapper.deleteByExample(example);
        //TODO 添加日志记录
    }

    @Override
    public void updateUserPassword(String baseId, String userName, String password) {
        String salt = CryptoUtils.getSalt();
        UserPasswordLoginInfo loginInfo = new UserPasswordLoginInfo();
        loginInfo.setSalt(salt);
        loginInfo.setPassword(CryptoUtils.getHash(password, salt));
        Example example = new Example(UserPasswordLoginInfo.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("userId", baseId);
        if (StringUtils.isNotBlank(userName)) {
            criteria.andEqualTo("username", userName);
        }
        loginInfoMapper.updateByExampleSelective(loginInfo, example);
        //TODO 添加日志记录
    }

    @Override
    public void updateUserPassword(String baseId, String password) {
        this.updateUserPassword(baseId, null, password);
    }
}
