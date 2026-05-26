package com.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Article;
import com.blog.service.ArticleService;
import com.blog.utils.ApiResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/list")
    public ApiResult<Page<Article>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long categoryId) {
        return ApiResult.success(articleService.pageByCategory(pageNum, pageSize, categoryId));
    }

    @GetMapping("/detail/{id}")
    public ApiResult<Article> detail(@PathVariable Long id) {
        Article article = articleService.getDetail(id);
        if (article == null) {
            return ApiResult.error("文章不存在");
        }
        return ApiResult.success(article);
    }

    @PostMapping("/create")
    public ApiResult<Article> create(@RequestBody Article article) {
        return ApiResult.success(articleService.create(article));
    }

    @PutMapping("/update")
    public ApiResult<Article> update(@RequestBody Article article) {
        return ApiResult.success(articleService.update(article));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<?> delete(@PathVariable Long id) {
        articleService.deleteById(id);
        return ApiResult.success();
    }
}
