package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.Category;

public interface CategoryService extends IService<Category> {
    Category create(Category category);
    void deleteById(Long id);
}
