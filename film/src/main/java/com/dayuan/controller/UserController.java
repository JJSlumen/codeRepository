package com.dayuan.controller;

import com.dayuan.entity.User;
import com.dayuan.mapper.UserMapper;
import com.dayuan.util.AjaxResult;
import com.dayuan.util.MailUtil;
import com.dayuan.util.MyRandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("api/user")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "login.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public AjaxResult login(String cardNo,
                            String password,
                            HttpSession session) {

        User user = userMapper.selectByCardNo(cardNo);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("cardNo",cardNo);
            return AjaxResult.success();
        } else {
            return AjaxResult.fail();
        }

    }

    /**
     * 发送验证码
     *
     * @param session
     * @param email
     * @return
     */
    @PostMapping(value = "sendCode.do")
    @ResponseBody
    public AjaxResult sendCode(HttpSession session, @RequestParam String email) {

        try {

            //todo 安全、防刷机制

            String validateCode = MyRandomUtil.getRandom(6);

            MailUtil.sendTextMail(email, "邮箱验证", "验证码：" + validateCode);

            session.setAttribute("validateCode",validateCode);
            session.setAttribute(validateCode, System.currentTimeMillis());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.fail("邮件发送失败");
        }

        return AjaxResult.success();
    }


    @PostMapping(value = "signup.do")
    @ResponseBody
    public AjaxResult signup(HttpSession session
            , @RequestParam String loginName
            , @RequestParam String pwd
            , @RequestParam String validateCode) {

        try {

            String validateCode_session = (String) session.getAttribute("validateCode");
            Long preTimeMillis = (Long) session.getAttribute(validateCode_session);
            if(validateCode_session==null || !validateCode.equals(validateCode_session)){
                return AjaxResult.fail("验证码不正确");
            }else if (System.currentTimeMillis()-preTimeMillis>120000){
                return AjaxResult.fail("验证码已失效");
            }

            //todo 注册业务

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.fail("注册失败");
        }

        return AjaxResult.success();
    }


}
