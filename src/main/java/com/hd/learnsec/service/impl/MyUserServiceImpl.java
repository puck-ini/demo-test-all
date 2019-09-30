package com.hd.learnsec.service.impl;


import com.hd.learnsec.entity.MyUser;
import com.hd.learnsec.repository.MyUserRepository;
import com.hd.learnsec.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserServiceImpl implements MyUserService{

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<MyUser> getAll() {
        return myUserRepository.findAll();
    }

    @Override
    public MyUser insert(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return myUserRepository.save(myUser);
    }
}
