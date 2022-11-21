package com.example.sojha.splitwise.controller;

import com.example.sojha.splitwise.model.request.user.CreateUser;
import com.example.sojha.splitwise.model.response.user.UserInfo;
import com.example.sojha.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/create")
    public void createUser(@RequestBody CreateUser request) {
        userService.createUser(request);
    }

    @GetMapping("/user/info/{id}")
    @ResponseBody
    public UserInfo getUserInfo(@PathVariable Long id) {
        return userService.getUserInfoById(id);
    }
}
