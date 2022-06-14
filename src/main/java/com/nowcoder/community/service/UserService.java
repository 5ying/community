package com.nowcoder.community.service;/*
 *文件名: UserService
 *创建者: wwy
 *创建时间:2022/6/8 11:30
 *描述:
 */

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserId(int id){
        return userMapper.selectById(id);
    }
}
