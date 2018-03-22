package cn.irving.zhao.auth.web.controller;

import cn.irving.zhao.auth.web.constant.Constant;
import cn.irving.zhao.util.base.captcha.Captcha;
import cn.irving.zhao.util.base.captcha.CaptchaUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Irving
 * @version CaptchaController.java, v 0.1 2018/2/27
 */
@RequestMapping("/captcha")
public class CaptchaController {

    /**
     * 获得图片验证码
     */
    @RequestMapping("/pic")
    public void generatePicCaptcha(HttpServletResponse response) throws IOException {
        Captcha captcha = CaptchaUtil.generateCaptcha(200, 80, 4);
        Session session = SecurityUtils.getSubject().getSession(true);
        session.setAttribute(Constant.SESSION_PIC_VALID_CODE_KEY, captcha.getCode());
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
        captcha.write(response.getOutputStream(), "JPEG");
    }

    @RequestMapping("/pic/valid")
    public void validPicCaptcha(String valid) {
        //TODO 验证图片验证码
    }

    /**
     * 发送短信验证码
     */
    @RequestMapping("/mobile")
    @ResponseBody
    public void sendSmsValidCode(@RequestParam(name = "mobile") String mobile) {
        //TODO 发送短信验证码并返回结果
    }

    public void validSmsValidCode(@RequestParam(name = "mobile") String mobile,
                                  @RequestParam(name = "vercode") String vercode) {
        //TODO 验证短信验证码，添加 requestHead 或 session 等相关校验内容
    }

}
