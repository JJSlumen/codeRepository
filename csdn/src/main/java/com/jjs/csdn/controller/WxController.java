package com.jjs.csdn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjs.csdn.entity.User;
import com.jjs.csdn.entity.WxUserInfo;
import com.jjs.csdn.service.UserService;
import com.jjs.csdn.service.WxAuthenService;
import com.jjs.csdn.util.AjaxResult;
import com.jjs.csdn.util.ApiConnector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class WxController {

    @Resource
    WxAuthenService wxAuthenService;
    @Resource
    UserService userService;

    @RequestMapping("/codeUrl")
    @ResponseBody
    public AjaxResult codeUrl() {
        String url = "http://payhub.dayuanit.com/weixin/connect/qrconnect.do?" +
                "appid=2018101019365510690&redirect_uri=http://127.0.0.1:8080/loginCallback&response_type=code&scope=snsapi_login";
        return AjaxResult.success(url);
    }

    @RequestMapping("/loginCallback")
    public String loginCallback(String code, HttpSession session) {

        String accessTokenUrl = "http://payhub.dayuanit.com/weixin/sns/oauth2/access_token.do?appid=2018101019365510690&secret=01856405049204689921779748470570" +
                "&code=" + code + "&grant_type=authorization_code";

        String result = ApiConnector.get(accessTokenUrl, null);

        JSONObject jsonObject = JSON.parseObject(result);

        String accessToken = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");

        //获取微信用户信息
        String wxUserUrl = "http://payhub.dayuanit.com/weixin/sns/userinfo.do?access_token=" + accessToken + "&openid=" + openid;
        result = ApiConnector.get(wxUserUrl, null);
        JSONObject userInfoJsonObject = JSON.parseObject(result);

        //去绑定页面 or 自动登录去个人中心
        WxUserInfo wxUserInfo = wxAuthenService.getWxUserInfoByOpenId(openid);
        if (null == wxUserInfo) {

            //去绑定页面
            session.setAttribute("nickName", userInfoJsonObject.getString("nickname"));
            session.setAttribute("headimgurl", userInfoJsonObject.getString("headimgurl"));
            session.setAttribute("openid", openid);

            return "redirect:/html/bind.html";
        }
        return "/html/home.html";
    }

    @RequestMapping("/bind")
    @ResponseBody
    public AjaxResult bind(String userName, String password, HttpSession session) {

        try {
            User user = userService.login(userName, password);
            wxAuthenService.saveBindInfo(user.getId(), (String) session.getAttribute("openid"), (String) session.getAttribute("nickName"), (String) session.getAttribute("headimgurl"));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }

        return AjaxResult.success();
    }
}
