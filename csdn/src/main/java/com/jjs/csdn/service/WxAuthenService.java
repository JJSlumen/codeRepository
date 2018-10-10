package com.jjs.csdn.service;

import com.jjs.csdn.entity.WxUserInfo;
import com.jjs.csdn.mapper.WxUserInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WxAuthenService {

    @Resource
    WxUserInfoMapper wxUserInfoMapper;

    public WxUserInfo getWxUserInfoByOpenId(String openid){
        return wxUserInfoMapper.getWxUserInfoByOpenId(openid);
    }

    public void saveBindInfo(int userId, String openId, String nickName, String headUrl) throws Exception {
        WxUserInfo wxUserInfo = new WxUserInfo();
        wxUserInfo.setUserId(userId);
        wxUserInfo.setOpenid(openId);
        wxUserInfo.setNickname(nickName);
        wxUserInfo.setHeadimgurl(headUrl);
        int row = wxUserInfoMapper.insert(wxUserInfo);
        if (1 != row) {
            throw new Exception("绑定失败");
        }
    }
}
