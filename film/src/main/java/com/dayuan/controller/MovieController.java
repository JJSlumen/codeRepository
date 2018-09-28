package com.dayuan.controller;

import com.dayuan.entity.Movie;
import com.dayuan.entity.ShowDay;
import com.dayuan.entity.ShowTime;
import com.dayuan.mapper.MovieMapper;
import com.dayuan.mapper.ShowDayMapper;
import com.dayuan.mapper.ShowTimeMapper;
import com.dayuan.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("api")
public class MovieController {

    @Autowired
    MovieMapper movieMapper;
    @Autowired
    ShowDayMapper showDayMapper;
    @Autowired
    ShowTimeMapper showTimeMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("listMovies.do")
    @ResponseBody
    public AjaxResult listMovies() {
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();

        Object listMovies = stringObjectValueOperations.get("listMovies");
        if (listMovies != null) {
            return AjaxResult.success(listMovies);
        }

        listMovies = movieMapper.listMovies();
        stringObjectValueOperations.set("listMovies", listMovies, 10, TimeUnit.SECONDS);

        return AjaxResult.success(listMovies);
    }


    @PostMapping("getMovieInfo.do")
    @ResponseBody
    public AjaxResult getMovieInfo(@RequestParam("id") Integer id) {
        return AjaxResult.success(movieMapper.selectByPrimaryKey(id));
    }

    /**
     * 查询放映日期
     *
     * @param id 电影id
     * @return
     */
    @PostMapping("listShowDay.do")
    @ResponseBody
    public AjaxResult listShowDay(@RequestParam("id") Integer id) {
        return AjaxResult.success(showDayMapper.listShowday(id));
    }

    @PostMapping("listShowTime.do")
    @ResponseBody
    public AjaxResult listShowTime(@RequestParam("id") Integer id) {
        List<List<ShowTime>> showTimeList = new ArrayList<>();
        List<ShowDay> showDays = showDayMapper.listShowday(id);
        for (ShowDay showDay :
                showDays) {
            List<ShowTime> showTimes = showTimeMapper.selectShowTimes(showDay.getId());
            showTimeList.add(showTimes);
        }

        return AjaxResult.success(showTimeList);
    }

    @PostMapping("chooseSeat.do")
    @ResponseBody
    public AjaxResult chooseSeat(@RequestParam("name") String name,
                                 @RequestParam("time") String time,
                                 @RequestParam("dayId") Integer dayId) {
        HashMap<String, Object> chooseSeatInfo = new HashMap<>();
        ShowDay showDay = showDayMapper.selectByPrimaryKey(dayId);
        String showDayCol = showDay.getShowDayCol();
        chooseSeatInfo.put("day", showDayCol);
        chooseSeatInfo.put("name", name);
        chooseSeatInfo.put("time", time);
        return AjaxResult.success(chooseSeatInfo);
    }

    @RequestMapping(value = "getMovieInfoByShowTimeId.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult getMovieInfoByShowTimeId(@RequestParam("showTimeId") Integer showTimeId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        ShowTime showTime = showTimeMapper.selectByPrimaryKey(showTimeId);
        String showTimeCol = showTime.getShowTimeCol();
        Integer showDayId = showTime.getShowDayId();
        ShowDay showDay = showDayMapper.selectByPrimaryKey(showDayId);
        String showDayCol = showDay.getShowDayCol();
        Movie movie = movieMapper.selectByShowTimeId(showTimeId);

        hashMap.put("showTimeCol", showTimeCol);
        hashMap.put("showDayCol", showDayCol);
        hashMap.put("movie", movie);
        return AjaxResult.success(hashMap);
    }
}
