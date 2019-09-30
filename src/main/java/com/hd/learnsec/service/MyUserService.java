package com.hd.learnsec.service;

import com.hd.learnsec.entity.MyUser;

import java.util.List;

public interface MyUserService {

    List<MyUser> getAll();

    MyUser insert(MyUser myUser);
}
