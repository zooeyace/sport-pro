package com.zyy.sport.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class EasyUser implements UserDetails {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String nickname;

    private boolean status;

    private boolean admin;

    private String avatarUrl;

    private Integer sex;

    private String openId;

    private String address;

    private String phoneNumber;

    private List<Role> roles;

    private List<Menu> menus;

    private List<Permission> permissions;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                if (StringUtils.isNotEmpty(role.getName()))
                    list.add(new SimpleGrantedAuthority(role.getName()));
            }
        }
        if (permissions != null && permissions.size() > 0) {
            permissions.forEach(item -> {
                if (StringUtils.isNotEmpty(item.getName())) {
                    list.add(new SimpleGrantedAuthority(item.getName()));
                }
            });
        }
        return list;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return status;
    }
}
