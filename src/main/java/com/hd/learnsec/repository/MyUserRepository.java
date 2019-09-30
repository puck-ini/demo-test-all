package com.hd.learnsec.repository;


import com.hd.learnsec.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyUserRepository extends JpaRepository<MyUser,Integer>{

    List<MyUser> findByUserName(String username);
}
