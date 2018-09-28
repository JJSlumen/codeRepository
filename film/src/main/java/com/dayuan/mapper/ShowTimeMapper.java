package com.dayuan.mapper;

import com.dayuan.entity.ShowTime;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShowTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShowTime record);

    ShowTime selectByPrimaryKey(Integer id);

    List<ShowTime> selectShowTimes(@Param("showDayId") Integer showDayId);

}