package cn.irving.zhao.auth.oauth.manager.impl;

import cn.irving.zhao.auth.oauth.manager.AccessTokenManager;
import cn.irving.zhao.auth.oauth.manager.AuthCodeManager;
import cn.irving.zhao.auth.oauth.vo.OauthRequestWrapper;
import cn.irving.zhao.util.base.redis.RedisUtil;
import cn.irving.zhao.util.base.serial.ObjectByteSerialUtil;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.SafeEncoder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Irving
 * @version RedisAuthCodeManager.java, v 0.1 2018/2/26
 */
public class RedisAuthCodeManager implements AuthCodeManager {

    @Resource
    private AccessTokenManager tokenManager;

    @Override
    public String saveOauthRequest(OauthRequestWrapper request) {
        try (ShardedJedis shardedJedis = RedisUtil.getResources()) {
            String id = UUID.randomUUID().toString();
            byte[] requestBytes = ObjectByteSerialUtil.serialize(request);
            shardedJedis.set(SafeEncoder.encode(id), requestBytes);
            return id;
        }
    }

    @Override
    public OauthRequestWrapper getOauthRequestById(String id) {
        try (ShardedJedis shardedJedis = RedisUtil.getResources()) {
            byte[] requestBytes = shardedJedis.get(SafeEncoder.encode(id));
            if (requestBytes == null || requestBytes.length == 0) {
                return null;
            } else {
                return (OauthRequestWrapper) ObjectByteSerialUtil.deserialize(requestBytes);
            }
        }
    }

    @Override
    public void delOauthRequestById(String id) {
        try (ShardedJedis shardedJedis = RedisUtil.getResources()) {
            shardedJedis.del(SafeEncoder.encode(id));
        }
    }

    @Override
    public String generateCode(String userId) {
        try (ShardedJedis shardedJedis = RedisUtil.getResources()) {
            OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
            String code = oAuthIssuer.authorizationCode();
            String accessToken = tokenManager.generateToken(userId);
            shardedJedis.set(code, accessToken, "nx", "ex", AUTH_CODE_EXPIRE);
            return code;
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAccessTokenByCode(String authCode) {
        try (ShardedJedis shardedJedis = RedisUtil.getResources()) {
            //授权码仅可使用一次
            String token = shardedJedis.getSet(authCode, "");
            shardedJedis.del(authCode);
            return token;
        }
    }
}
