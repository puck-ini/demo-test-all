package com.hd.learnsec.controller;


import com.hd.learnsec.entity.MyUser;
import com.hd.learnsec.service.impl.MyUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class MyUserController {

    @Autowired
    private MyUserServiceImpl myUserService;




    @GetMapping("/getall")
    public List<MyUser> getAll(){
        return myUserService.getAll();
    }


    @PostMapping("/insert")
    public MyUser insert(@RequestParam("username") String username,
                         @RequestParam("password") String password){
        MyUser myUser = new MyUser();
        myUser.setUserName(username);
        myUser.setPassword(password);
        return myUserService.insert(myUser);


    }
}
