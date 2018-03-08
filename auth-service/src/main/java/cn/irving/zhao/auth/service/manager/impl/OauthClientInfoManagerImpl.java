package cn.irving.zhao.auth.service.manager.impl;

import cn.irving.zhao.auth.service.constant.DeleteEnum;
import cn.irving.zhao.auth.service.entity.OauthClientInfo;
import cn.irving.zhao.auth.service.manager.OauthClientInfoManager;
import cn.irving.zhao.auth.service.mapper.OauthClientInfoMapper;
import cn.irving.zhao.util.base.security.CryptoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Irving
 * @version OauthClientInfoManagerImpl.java, v 0.1 2018/2/22
 */
@Service
public class OauthClientInfoManagerImpl implements OauthClientInfoManager {

    @Resource
    private OauthClientInfoMapper clientMapper;

    @Override
    public CheckStatus checkOauthClient(String clientId, String clientSecurity) {
        if (StringUtils.isBlank(clientSecurity)) {
            return CheckStatus.NO_SIGN;
        }
        OauthClientInfo clientInfo = getOauthClientById(clientId);
        if (clientInfo == null) {
            return CheckStatus.NO_CLIENT;
        }
        if (CryptoUtils.verify(clientSecurity, clientId, clientInfo.getClientSalt())) {
            return CheckStatus.SUCCESS;
        } else {
            return CheckStatus.SIGN_ERROR;
        }
    }

    @Override
    public String updateOauthClientSecurity(String clientId) {
        String salt = CryptoUtils.getSalt();
        OauthClientInfo clientInfo = new OauthClientInfo();
        clientInfo.setClientSalt(salt);
        Example example = new Example(OauthClientInfo.class);
        example.createCriteria().andEqualTo("clientId", clientId);
        clientMapper.updateByExampleSelective(clientInfo, example);
        //TODO 添加日志
        return CryptoUtils.getHash(clientId, salt);
    }

    private OauthClientInfo getOauthClientById(String oauthClientId) {
        OauthClientInfo param = new OauthClientInfo();
        param.setClientId(oauthClientId);
        param.setIsDelete(DeleteEnum.N);//查询未删除
        List<OauthClientInfo> result = clientMapper.select(param);
        if (result == null || result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }
}
