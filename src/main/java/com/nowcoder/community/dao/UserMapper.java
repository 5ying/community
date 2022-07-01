package com.nowcoder.community.dao;/*
 *文件名: UserMapper
 *创建者: wwy
 *创建时间:2022/6/6 11:52
 *描述:
 */

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper {

    User selectById(int id);
    User seclectByName(String username);
    User seclectByEmail(String email);
    int insetUser(User user);
    int updateStatus(int id,int status);

    int updateHeader(int id,String headerUrl);
    int updatePassword(int id,String password);

}
