package com.wzc.controller;

import com.wzc.annotation.RequestLock;
import com.wzc.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    @RequestLock(prefixKey = "userinfo", expire = 60)
    public String getUserInfo(@RequestBody User user){
        return user == null ? "提交失败" : user.getUserPhone() + user.getUserName();
    }

}
