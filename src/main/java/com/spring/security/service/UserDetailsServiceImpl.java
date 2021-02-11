package com.spring.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.spring.security.model.RoleInfo;
import com.spring.security.repository.RoleInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.model.UserInfo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoService userInfoDAO;

    @Autowired
    RoleInfoRepository roleInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoDAO.getUserInfoByUserName(userName);
        List<RoleInfo> roleInfos = roleInfoRepository.findAllByUserName(userName);
        List<String> roleNames = new ArrayList<>();
        roleInfos.forEach(roleInfo -> {
            roleNames.add(roleInfo.getRole());
        });
        GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
        return new User(userInfo.getUserName(), userInfo.getPassword(), Arrays.asList(authority));
    }
}
