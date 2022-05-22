package com.zyy.sport.config;

import com.zyy.sport.constant.SpringSecurityConstant;
import com.zyy.sport.filter.JwtAuthenticationFilter;
import com.zyy.sport.security.JwtAccessDeniedHandler;
import com.zyy.sport.security.JwtAuthenticationEntryPoint;
import com.zyy.sport.security.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailServiceImpl userDetailService; // self

    @Resource
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private JwtAccessDeniedHandler accessDeniedHandler;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 实现自定义登录逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 白名单直接放行
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(SpringSecurityConstant.NONE_SECURITY_URL_PATTERNS);
    }

    /**
     * security核心配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用jwt，关闭csrf
        http.csrf().disable();
        //基于token认证，关闭session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //配置白名单  除白名单以外的都进行认证
        http.authorizeRequests().anyRequest().authenticated();
        //禁用缓存
        http.headers().cacheControl();
        //添加jwt登录授权的过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //添加未授权和未登录的返回结果
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}