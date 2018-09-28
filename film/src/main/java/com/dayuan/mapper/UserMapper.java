package com.dayuan.mapper;

import com.dayuan.entity.User;
import com.dayuan.entity.UserKey;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(UserKey key);

    int insert(User record);

    User selectByPrimaryKey(UserKey key);

    int updateByPrimaryKey(User record);

    User selectByCardNo(@Param("cardNo") String cardNo);
}