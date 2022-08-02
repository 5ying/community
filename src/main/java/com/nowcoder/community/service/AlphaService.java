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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

@Service
//@Scope("prototype")
public class AlphaService {

    private static final Logger logger = LoggerFactory.getLogger(AlphaService.class);

    @Autowired
    private AlphaDao alphaDao;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;
    
    @Autowired
    private TransactionTemplate transactionTemplate;

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

    
    public Object save2(){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        
        return  transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                //        新增用户
                User user = new User();
                user.setUsername("beta");
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
//                return null;
            }
        });
    }

    // 让该方法在多线程环境下,被异步的调用.

    /**
     * 启动一个线程调用该方法，这个方法和主线程是并发执行的，是异步调用
     */
    @Async
    public void execute1() {
        logger.debug("execute1");
    }

    @Scheduled(initialDelay = 10000, fixedRate = 1000)
    public void execute2() {
        logger.debug("execute2");
    }


 }
