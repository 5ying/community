package com.nowcoder.community.dao;/*
 *文件名: AlphaDaoHibernateImpl
 *创建者: wwy
 *创建时间:2022/5/31 15:56
 *描述:
 */

import org.springframework.stereotype.Repository;

@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao {
    @Override
    public String select() {
        return "Heibernate";
    }

}
