package com.blog.controller;

import com.blog.entity.User;
import com.blog.service.UserService;
import com.blog.utils.ApiResult;
import com.blog.utils.JwtUserContext;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResult<?> register(@RequestBody Map<String, String> body) {
        try {
            User user = userService.register(
                    body.get("username"),
                    body.get("password"),
                    body.get("email")
            );
            return ApiResult.success(Map.of("id", user.getId(), "username", user.getUsername()));
        } catch (RuntimeException e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ApiResult<?> login(@RequestBody Map<String, String> body) {
        try {
            String token = userService.login(body.get("username"), body.get("password"));
            return ApiResult.success(Map.of("token", token));
        } catch (RuntimeException e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @GetMapping("/info")
    public ApiResult<?> info() {
        User user = userService.getById(JwtUserContext.getUserId());
        if (user == null) {
            return ApiResult.error("用户不存在");
        }
        Map<String, Object> data = Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail() != null ? user.getEmail() : "",
                "role", user.getRole()
        );
        return ApiResult.success(data);
    }
}
