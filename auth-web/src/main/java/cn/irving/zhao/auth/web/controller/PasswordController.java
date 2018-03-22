package cn.irving.zhao.auth.web.controller;

import cn.irving.zhao.auth.service.constant.UserStatus;
import cn.irving.zhao.auth.service.entity.UserPasswordLoginInfo;
import cn.irving.zhao.auth.service.manager.UserPasswordLoginInfoManager;
import cn.irving.zhao.auth.web.constant.Constant;
import cn.irving.zhao.auth.web.shiro.AuthWebUser;
import cn.irving.zhao.platform.core.shiro.token.PasswordToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.security.auth.login.AccountException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Irving
 * @version PasswordController.java, v 0.1 2018/2/27
 */
@RequestMapping("/password")
@Slf4j
public class PasswordController {

    @Resource
    private UserPasswordLoginInfoManager passwordLoginInfoManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam(name = "username") String username,
                                     @RequestParam(name = "password") String password,
                                     @RequestParam(name = "captcha", defaultValue = "") String captcha,
                                     @RequestParam(name = "redirectUrl", required = false) String redirectUrl) {
        Map<String, Object> result = new HashMap<>();
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(true);
            String sessionCaptcha = session.getAttribute(Constant.SESSION_PIC_VALID_CODE_KEY).toString().toUpperCase();
            if (captcha.toUpperCase().equals(sessionCaptcha)) {
                PasswordToken passwordToken = new PasswordToken(username, password);
                subject.login(passwordToken);
                AuthWebUser authWebUser = (AuthWebUser) subject.getPrincipal();
                if (authWebUser.getUserBaseInfo().getStatus() == UserStatus.DISABLE) {
                    result.put("success", false);
                    result.put("message", "账号被停用");
                } else {
                    result.put("success", true);
                    result.put("message", "登陆成功");
                    if (StringUtils.isBlank(redirectUrl)) {
                        redirectUrl = "/";
                    }
                    result.put("redirectUrl", redirectUrl);
                }
            } else {
                result.put("success", false);
                result.put("message", "验证码错误");
            }
        } catch (UnknownAccountException e) {
            result.put("message", e.getMessage());
        } catch (LockedAccountException e) {
            result.put("message", "账号异常，请联系管理员");
        } catch (DisabledAccountException e) {
            result.put("message", "账号停用，请联系管理员");
        } catch (IncorrectCredentialsException e) {
            result.put("message", "账号或密码错误");
        } catch (Exception e) {
            result.put("message", "系统异常");
            log.error("系统异常", e);
        }

        result.put("success", false);
        return result;
    }

    @RequestMapping(name = "/register", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestParam("mobile") String mobile,
                                        @RequestParam("password") String password,
                                        @RequestParam("vercode") String vercode) {
        Map<String, Object> result = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        String sessionMobile = String.valueOf(session.getAttribute(Constant.SESSION_SMS_VALID_MOBILE_KEY));
        String sessionVercode = String.valueOf(session.getAttribute(Constant.SESSION_SMS_VALID_CODE_KEY));
        if (mobile.equals(sessionMobile) && vercode.equals(sessionVercode)) {
            session.removeAttribute(Constant.SESSION_SMS_VALID_CODE_KEY);
            session.removeAttribute(Constant.SESSION_SMS_VALID_MOBILE_KEY);
            try {
                passwordLoginInfoManager.addNewUser(mobile, password);
                PasswordToken passwordToken = new PasswordToken(mobile, password);
                subject.login(passwordToken);
                //TODO 注册之后的重定向地址
            } catch (AccountException e) {
                result.put("success", false);
                result.put("message", "账号已存在");
            }
        } else {
            result.put("success", false);
            result.put("message", "验证码失效，请重新获取");
        }
        return result;
    }

    @RequestMapping(value = "/forget", method = RequestMethod.POST)
    public Map<String, Object> resetPassword(String mobile,
                                             String password,
                                             String vercode) {
        Map<String, Object> result = new HashMap<>();
        UserPasswordLoginInfo loginInfo = passwordLoginInfoManager.getUserPasswordLoginInfoByUserName(mobile);
        if (loginInfo == null) {
            result.put("success", "false");
            result.put("message", "用户名不存在");
        } else {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(true);
            String sessionMobile = String.valueOf(session.getAttribute(Constant.SESSION_SMS_VALID_MOBILE_KEY));
            String sessionVercode = String.valueOf(session.getAttribute(Constant.SESSION_SMS_VALID_CODE_KEY));
            if (mobile.equals(sessionMobile) && vercode.equals(sessionVercode)) {
                session.removeAttribute(Constant.SESSION_SMS_VALID_MOBILE_KEY);
                session.removeAttribute(Constant.SESSION_SMS_VALID_CODE_KEY);
                passwordLoginInfoManager.updateUserPassword(loginInfo.getUserId(), mobile, password);
                PasswordToken passwordToken = new PasswordToken(mobile, password);
                subject.login(passwordToken);
                result.put("success", true);
                result.put("message", "密码修改成功");
                //TODO 修改密码之后的重定向
            } else {
                result.put("success", false);
                result.put("message", "验证码失效，请重新获取");
            }
        }
        return result;
    }


//    private OAuthResponse generateOauthResponse(String requestId) {
//        try {
//            OauthRequestWrapper requestWrapper = authCodeManager.getOauthRequestById(requestId);
//            AuthWebUser webUser = (AuthWebUser) SecurityUtils.getSubject().getPrincipal();
//            String authCode = authCodeManager.generateCode(webUser.getUserId());
//            OAuthResponse.OAuthResponseBuilder responseBuilder = OAuthASResponse.status(HttpServletResponse.SC_FOUND);
//            responseBuilder.location(requestWrapper.getRedirectURI());
//            responseBuilder.setParam(OAuth.OAUTH_STATE, requestWrapper.getParam(OAuth.OAUTH_STATE));
//            responseBuilder.setParam(OAuth.OAUTH_CODE, authCode);
//            return responseBuilder.buildQueryMessage();
//        } catch (OAuthSystemException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    //登陆
    //注册
    //忘记密码

}
