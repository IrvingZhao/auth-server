package cn.irving.zhao.auth.service.manager.impl;

import cn.irving.zhao.auth.service.entity.UserPasswordLoginInfo;
import cn.irving.zhao.auth.service.manager.UserPasswordLoginInfoManager;
import cn.irving.zhao.auth.service.mapper.UserPasswordLoginInfoMapper;
import cn.irving.zhao.util.base.security.CryptoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author Irving
 * @version UserPasswordLoginInfoManager.java, v 0.1 2018/2/22
 * @project userProfile
 */
@Service
public class UserPasswordLoginInfoManagerImpl implements UserPasswordLoginInfoManager {

    @Resource
    private UserPasswordLoginInfoMapper loginInfoMapper;

    @Override
    public void addNewUser(String userName, String password) {
        // 先根据用户名查询，存在则抛出用户已存在异常
        // 然后创建 baseinfo
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
        passwordLoginInfo.setBaseId(baseId);
        passwordLoginInfo.setUsername(userName);
        passwordLoginInfo.setPassword(CryptoUtils.getHash(password, salt));//加盐加密
        passwordLoginInfo.setSalt(salt);
        passwordLoginInfo.setId(UUID.randomUUID().toString().replace("-", ""));
        loginInfoMapper.insertSelective(passwordLoginInfo);
        //TODO 添加日志记录
    }

    @Override
    public void addUserPasswordLoginInfo(String baseId, String userName) {
        Example searchByBaseId = new Example(UserPasswordLoginInfo.class);
        searchByBaseId.createCriteria().andEqualTo("baseId", baseId);
        List<UserPasswordLoginInfo> loginInfoList = loginInfoMapper.selectByExample(searchByBaseId);
        if (loginInfoList != null && loginInfoList.size() > 0) {
            UserPasswordLoginInfo tempLoginInfo = loginInfoList.get(0);
            tempLoginInfo.setId(UUID.randomUUID().toString().replace("-", ""));
            tempLoginInfo.setUsername(userName);
            loginInfoMapper.insert(tempLoginInfo);
            //TODO 添加日志记录
        }
    }

    @Override
    public void removeUserPasswordLoginInfo(String baseId, String userName) {
        Example example = new Example(UserPasswordLoginInfo.class);
        example.createCriteria().andEqualTo("baseId", baseId)
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
        Example.Criteria criteria = example.createCriteria().andEqualTo("baseId", baseId);
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
