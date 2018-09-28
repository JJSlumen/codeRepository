package com.dayuan.mapper;

import com.dayuan.entity.Movie;

import java.util.List;

public interface MovieMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Movie record);


    Movie selectByPrimaryKey(Integer id);

    List<Movie> listMovies();

    Movie selectByShowTimeId(Integer showTimeId);
}