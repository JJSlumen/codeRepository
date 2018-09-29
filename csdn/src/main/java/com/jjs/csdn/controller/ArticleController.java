package com.jjs.csdn.controller;

import com.jjs.csdn.entity.Article;
import com.jjs.csdn.mapper.ArticleMapper;
import com.jjs.csdn.util.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("article")
public class ArticleController {
    @Resource
    ArticleMapper articleMapper;

    @RequestMapping(value = "getArticle",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public AjaxResult getArticle(Integer id){
        Article article = articleMapper.selectByPrimaryKey(id);
        return AjaxResult.success(article);
    }

    @RequestMapping(value = "editContent",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public AjaxResult editContent(Integer id,
                                  String content){
        Article article = new Article();
        article.setId(id);
        article.setContent(content);
        articleMapper.updateByPrimaryKeySelective(article);
        return AjaxResult.success();
    }
}
