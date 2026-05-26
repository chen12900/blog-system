package com.blog.controller;

import com.blog.entity.Comment;
import com.blog.service.CommentService;
import com.blog.utils.ApiResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/list/{articleId}")
    public ApiResult<List<Comment>> list(@PathVariable Long articleId) {
        return ApiResult.success(commentService.listByArticleId(articleId));
    }

    @PostMapping("/add")
    public ApiResult<Comment> add(@RequestBody Comment comment) {
        return ApiResult.success(commentService.add(comment));
    }
}
