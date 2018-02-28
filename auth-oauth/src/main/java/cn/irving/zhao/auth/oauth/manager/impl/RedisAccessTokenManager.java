package cn.irving.zhao.auth.oauth.manager.impl;

import cn.irving.zhao.auth.oauth.manager.AccessTokenManager;
import cn.irving.zhao.util.base.redis.RedisUtil;
import cn.irving.zhao.util.base.security.MessageDigestSecurity;
import redis.clients.jedis.ShardedJedis;

/**
 * @author Irving
 * @version RedisAccessTokenManager.java, v 0.1 2018/2/26
 */
public class RedisAccessTokenManager implements AccessTokenManager {

    @Override
    public String generateToken(String objectId) {
        String tokenResult;
        try (ShardedJedis shardedJedis = RedisUtil.getResources()) {
            tokenResult = MessageDigestSecurity.MD5.encrypt(objectId + System.currentTimeMillis());
            shardedJedis.set(tokenResult, objectId, "nx", "ex", TOKEN_EXPIRE_TIME);
        }
        return tokenResult;
    }

    @Override
    public Boolean validToken(String token) {
        try (ShardedJedis shardedJedis = RedisUtil.getResources()) {
            return shardedJedis.exists(token);
        }
    }

    @Override
    public String getObjectIdByToken(String token) {
        try (ShardedJedis shardedJedis = RedisUtil.getResources()) {
            return shardedJedis.get(token);
        }
    }

    @Override
    public void dropAccessToken(String token) {
        try (ShardedJedis shardedJedis = RedisUtil.getResources()) {
            shardedJedis.del(token);
        }
    }

}
