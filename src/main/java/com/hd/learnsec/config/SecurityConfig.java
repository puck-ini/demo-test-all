package com.hd.learnsec.config;


import com.hd.learnsec.component.MyAuthenticationFailureHandler;
import com.hd.learnsec.component.MyAuthenticationSucessHandler;
import com.hd.learnsec.component.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private MyAuthenticationSucessHandler authenticationSucessHandler;

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()// 表单方式
                .loginPage("/authentication/require")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSucessHandler)// 处理登录成功
                .failureHandler(authenticationFailureHandler)// 处理登录失败
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
                .tokenValiditySeconds(3600)// remember 过期时间，单为秒
                .userDetailsService(userDetailsService)// 处理自动登录逻辑
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        "/login.html",
                        "/code/image").permitAll()// 授权配置
                .anyRequest()// 所有请求
//                .permitAll().and().csrf().disable();//都无需认证
                .authenticated()// 都需要认证
                .and().csrf().disable();
//        http.httpBasic().and().authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
