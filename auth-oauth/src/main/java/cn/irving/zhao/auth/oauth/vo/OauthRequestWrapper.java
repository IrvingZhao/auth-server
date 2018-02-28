package cn.irving.zhao.auth.oauth.vo;

import cn.irving.zhao.util.base.serial.ObjectStringSerialUtil;
import lombok.NoArgsConstructor;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;

import javax.servlet.http.HttpServletRequest;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Irving
 * @version OauthRequestWrapper.java, v 0.1 2018/2/26
 */
@NoArgsConstructor
public class OauthRequestWrapper implements Externalizable {

    private OAuthRequest oAuthRequest;

    private Map paramMap;

    private static Field requestField;

    static {
        try {
            requestField = OAuthRequest.class.getDeclaredField("request");
            requestField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public OauthRequestWrapper(OAuthRequest oAuthRequest) {
        this.oAuthRequest = oAuthRequest;
    }

    public String getParam(String name) {
        Object returnValue = null;
        if (oAuthRequest != null) {
            returnValue = oAuthRequest.getParam(name);
        } else if (paramMap != null) {
            returnValue = paramMap.get(name);
        }
        if (returnValue == null || !String.class.isInstance(returnValue)) {
            return null;
        } else {
            return String.valueOf(returnValue);
        }
    }

    public String getClientId() {
        Object returnValue = null;
        if (oAuthRequest != null) {
            returnValue = oAuthRequest.getClientId();
        } else if (paramMap != null) {
            returnValue = paramMap.get(OAuth.OAUTH_CLIENT_ID);
        }
        if (returnValue == null || !String.class.isInstance(returnValue)) {
            return null;
        } else {
            return String.valueOf(returnValue);
        }
    }

    public String getRedirectURI() {
        Object returnValue = null;
        if (oAuthRequest != null) {
            returnValue = oAuthRequest.getRedirectURI();
        } else if (paramMap != null) {
            returnValue = paramMap.get(OAuth.OAUTH_REDIRECT_URI);
        }
        if (returnValue == null || !String.class.isInstance(returnValue)) {
            return null;
        } else {
            return String.valueOf(returnValue);
        }
    }

    public String getClientSecret() {
        Object returnValue = null;
        if (oAuthRequest != null) {
            returnValue = oAuthRequest.getClientSecret();
        } else if (paramMap != null) {
            returnValue = paramMap.get(OAuth.OAUTH_CLIENT_SECRET);
        }
        if (returnValue == null || !String.class.isInstance(returnValue)) {
            return null;
        } else {
            return String.valueOf(returnValue);
        }
    }

    public Set<String> getScopes() {
        Object returnValue = null;
        if (oAuthRequest != null) {
            returnValue = oAuthRequest.getClientSecret();
        } else if (paramMap != null) {
            returnValue = paramMap.get(OAuth.OAUTH_SCOPE);
        }
        if (returnValue == null || !Set.class.isInstance(returnValue)) {
            return null;
        } else {
            return (Set) returnValue;
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        HttpServletRequest request = null;
        Map<String, String> dataMap = new HashMap<>();
        try {
            request = (HttpServletRequest) requestField.get(oAuthRequest);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (request != null) {
            request.getParameterMap().forEach((key, values) -> {
                dataMap.put(key, (values.length > 0 ? values[0] : null));
            });
        }
        String dataJson = ObjectStringSerialUtil.getSerialUtil().serial(dataMap, ObjectStringSerialUtil.SerialType.JSON);
        out.write(dataJson.getBytes());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        byte[] jsonBytes = new byte[in.available()];
        in.read(jsonBytes);
        paramMap = ObjectStringSerialUtil.getSerialUtil().parse(new String(jsonBytes), HashMap.class, ObjectStringSerialUtil.SerialType.JSON);
    }
}
