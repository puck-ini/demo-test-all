package com.hd.learnsec.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "hello spring security";
    }

    @GetMapping("/index")
    public Object index(){
        return SecurityContextHolder.getContext().getAuthentication();//获取Authentication对象信息
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/admin")
    public String adminTest(){

        return "admin test";
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/user")
    public String userTest(){

        return "user test";
    }

}
