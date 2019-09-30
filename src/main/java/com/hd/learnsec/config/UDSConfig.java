package com.hd.learnsec.config;


import com.hd.learnsec.entity.MyUser;
import com.hd.learnsec.repository.MyUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class UDSConfig implements UserDetailsService {

    private  Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserRepository myUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<MyUser> myUserList = new ArrayList<>();
        try {
            myUserList = myUserRepository.findByUserName(username);
        }catch (Exception e){
            logger.info("user null");
            logger.info(e.getMessage());
            return null;
        }

        MyUser user = myUserList.get(0);


        // 输出加密后的密码
        logger.info(user.getPassword());

        return new User(user.getUserName(), user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

}
