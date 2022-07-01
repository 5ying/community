package com.nowcoder.community.dao;/*
 *文件名: AlphaDaoMyBatisImpl
 *创建者: wwy
 *创建时间:2022/5/31 16:00
 *描述:
 */

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary  //优先调用
public class AlphaDaoMyBatisImpl implements  AlphaDao {

    @Override
    public String select() {
        return "MyBatis";
    }
}
