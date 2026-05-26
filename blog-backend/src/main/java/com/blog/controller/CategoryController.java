package com.blog.controller;

import com.blog.entity.Category;
import com.blog.service.CategoryService;
import com.blog.utils.ApiResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ApiResult<List<Category>> list() {
        return ApiResult.success(categoryService.list());
    }

    @PostMapping("/create")
    public ApiResult<Category> create(@RequestBody Category category) {
        return ApiResult.success(categoryService.create(category));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<?> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ApiResult.success();
    }
}
