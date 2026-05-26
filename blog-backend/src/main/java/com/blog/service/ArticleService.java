package com.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.Article;

public interface ArticleService extends IService<Article> {
    Page<Article> pageByCategory(Integer pageNum, Integer pageSize, Long categoryId);
    Article getDetail(Long id);
    Article create(Article article);
    Article update(Article article);
    void deleteById(Long id);
}
