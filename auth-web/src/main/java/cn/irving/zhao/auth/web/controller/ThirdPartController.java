package cn.irving.zhao.auth.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhaojn1
 * @version ThirdPartController.java, v 0.1 2018/2/27 zhaojn1
 * @project userProfile
 */
@RequestMapping("/third")
public class ThirdPartController {

    @RequestMapping("/login")
    public void login(String platform, String redirectUrl) {
        //TODO 保存平台和重定向地址 并返回生成码
        //TODO 获取授权登陆地址
    }

    public void loginCallback() {

    }

    //注册
    //登陆
}
