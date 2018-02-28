package cn.irving.zhao.auth.service.manager.impl;

import cn.irving.zhao.auth.service.entity.UserThirdLoginInfo;
import cn.irving.zhao.auth.service.manager.UserThirdLoginInfoManager;
import cn.irving.zhao.auth.service.mapper.UserThirdLoginInfoMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author Irving
 * @version UserThirdLoginInfoManagerImpl.java, v 0.1 2018/2/24
 */
@Service
public class UserThirdLoginInfoManagerImpl implements UserThirdLoginInfoManager {

    @Resource
    private UserThirdLoginInfoMapper loginInfoMapper;

    @Override
    public UserThirdLoginInfo getThirdLoginInfo(String platform, String thirdId) {
        Example example = new Example(UserThirdLoginInfo.class);
        example.createCriteria().andEqualTo("platform", platform)
                .andEqualTo("thirdId", thirdId);
        List<UserThirdLoginInfo> loginInfo = loginInfoMapper.selectByExample(example);
        return loginInfo == null || loginInfo.isEmpty() ? null : loginInfo.get(0);
    }

    @Override
    public void addThirdLoginInfo(String baseId, String platform, String thirdId) {
        UserThirdLoginInfo loginInfo = new UserThirdLoginInfo();
        loginInfo.setId(UUID.randomUUID().toString().replace("-", ""));
        loginInfo.setBaseId(baseId);
        loginInfo.setPlatform(platform);
        loginInfo.setThirdId(thirdId);
        loginInfoMapper.insertSelective(loginInfo);
        //TODO 添加日志
    }

    @Override
    public void updateThirdLoginInfo(String baseId, String platform, String newThirdId) {
        UserThirdLoginInfo loginInfo = new UserThirdLoginInfo();
        loginInfo.setThirdId(newThirdId);
        Example example = new Example(UserThirdLoginInfo.class);
        example.createCriteria().andEqualTo("baseId", baseId)
                .andEqualTo("platform", platform);
        loginInfoMapper.updateByExampleSelective(loginInfo, example);
        //TODO 添加日志
    }

    @Override
    public void removeThirdLoginInfo(String baseId, String platform) {
        Example example=new Example(UserThirdLoginInfo.class);
        example.createCriteria().andEqualTo("baseId",baseId)
                .andEqualTo("platform",platform);
        loginInfoMapper.deleteByExample(example);//TODO 考虑是否添加 IS_DELETE
        //TODO 添加日志
    }
}
