package com.nowcoder.community.service;/*
 *文件名: alphaService
 *创建者: wwy
 *创建时间:2022/5/31 16:07
 *描述:
 */

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    public String find(){
        return alphaDao.select();
    }

    public AlphaService(){
        System.out.println("实例化构造方法");
    }

    @PostConstruct
    public void init(){
        System.out.println("初始化");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("销毁方法");
    }

    /*
    *事务传播机制
    *REQUIRED :支持当前事务（外部事务，外部事物不存在，则创建新事务）
    * REQUIRES_NEW ：创建一个新的事务，并且暂停当前事务（外部事物）
    * NESTED：如当前存在事务（外部事务），则嵌套在该事务中执行（独立的提交和回滚），否则就会和REQUIRED一样。
    * */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public  Object savel(){
//        新增用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5("123"+user.getSalt()));
        user.setEmail("alpha@qq.com");
        user.setHeaderUrl("http://image.nowcoder.com/head/99.pang");
        user.setCreateTime(new Date());
        userMapper.insetUser(user);

//       新增帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setCreateTime(new Date());
        post.setContent("sakjfjasdfjdf0");
        post.setTitle("fhjksdjf");
        discussPostMapper.insertDiscussPostRows(post);


        Integer .valueOf("abc");
        return "ok";
    }



 }
