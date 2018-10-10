package com.jjs.csdn.mapper;

import com.jjs.csdn.entity.WxUserInfo;
import org.apache.ibatis.annotations.Param;

public interface WxUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxUserInfo record);

    int insertSelective(WxUserInfo record);

    WxUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxUserInfo record);

    int updateByPrimaryKey(WxUserInfo record);

    WxUserInfo getWxUserInfoByOpenId(@Param("openid") String openid);

}