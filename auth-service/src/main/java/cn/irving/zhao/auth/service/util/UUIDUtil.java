package cn.irving.zhao.auth.service.util;

import java.util.UUID;

/**
 * @author zhaojn1
 * @version UUIDUtil.java, v 0.1 2018/3/1 zhaojn1
 * @project userProfile
 */
public class UUIDUtil {

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
