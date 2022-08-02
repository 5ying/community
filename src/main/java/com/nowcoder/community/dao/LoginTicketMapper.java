package com.nowcoder.community.dao;/*
 *文件名: LoginTicketMapper
 *创建者: wwy
 *创建时间:2022/6/27 16:29
 *描述:
 */

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
@Deprecated
/*
这个组件不推荐使用
本组件是采用MySQL数据库的方式存储登录凭证，后期优化为Redsi存储
 */
public interface LoginTicketMapper {
//    int insertLoginTicket(LoginTicket loginTicket);
//
//    LoginTicket selectByTicket(String ticket);

//    int updataStatus(String ticket,int status);

    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);



}

