package com.nowcoder.community.controller;/*
 *文件名: DiscussPostController
 *创建者: wwy
 *创建时间:2022/6/23 15:24
 *描述:
 */

import com.nowcoder.community.Event.EventProducer;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.CommentService;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;
    @Autowired
    CommentService commentService;

    @Autowired
    private LikeService likeService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title,String content){
        User user = hostHolder.getUser();
        if (user==null){
            return CommunityUtil.getJSONString(403,"你还没有登录哦！");
        }

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);
//        int cnd = discussPostService.addDiscussPost(post);
//        System.out.println(cnd);

//        触发发帖事件
        Event event = new Event()
                .setTopic(TOPIC_PUBLISH)
                .setUserId(user.getId())
                .setEntityType(ENTITY_TYPE_POST)
                .setEntityId(post.getId());

        eventProducer.fireEvent(event);

//        计算帖子分数
        // 计算帖子分数
        String redisKey = RedisKeyUtil.getPostScoreKey();
        redisTemplate.opsForSet().add(redisKey, post.getId());


//        报错的情况，将来统一处理
        return CommunityUtil.getJSONString(0,"发布成功!");
    }


    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model, Page page){
//        帖子
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post",post);
//作者
        User user = userService.findUserId(post.getUserId());
        model.addAttribute("user",user);
//点赞
        long likeCount  = likeService.findEntityLikeCount(ENTITY_TYPE_POST,discussPostId);
        model.addAttribute("likeCount",likeCount);
//       当前用户的点赞状态
        int likeStatus =hostHolder.getUser()==null?0: likeService.findEntityLikeStatus(hostHolder.getUser().getId(),ENTITY_TYPE_POST,discussPostId);
        model.addAttribute("likeStatus",likeStatus);
//    评论的分页信息
        page.setLimit(5);
        page.setPath("/discuss/detail/"+discussPostId);
        page.setRows(post.getCommentCount());

//        评论：给帖子的评论
//        回复：给评论的评论
     List<Comment> commentList = commentService.findCommentsByEntity(ENTITY_TYPE_POST,
             post.getId(),page.getOffset(),page.getLimit());
//     评论Vo列表
     List<Map<String,Object>> commentVoList = new ArrayList<>();
     if (commentList!=null){
         for (Comment comment:commentList) {
//             一个帖子的评论Vo
             Map<String, Object> commentVo = new HashMap<>();
             commentVo.put("comment", comment);
             commentVo.put("user", userService.findUserId(comment.getUserId()));

             //点赞
             likeCount  = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT,comment.getId());
            commentVo.put("likeCount",likeCount);
//       当前用户的点赞状态
              likeStatus =hostHolder.getUser()==null?0: likeService.findEntityLikeStatus(hostHolder.getUser().getId(),ENTITY_TYPE_COMMENT,comment.getId());
             commentVo.put("likeStatus",likeStatus);

//             回复列表
             List<Comment> replyList = commentService.findCommentsByEntity(ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
//             回复的Vo列表
             List<Map<String, Object>> replyVoList = new ArrayList<>();
             if (commentList != null) {
                 for (Comment reply : replyList) {
//             一个帖子的评论Vo
                     Map<String, Object> replyVo = new HashMap<>();
                     replyVo.put("reply", reply);
                     replyVo.put("user", userService.findUserId(reply.getUserId()));

                     //点赞
                     likeCount  = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT,reply.getId());
                     replyVo.put("likeCount",likeCount);
//       当前用户的点赞状态
                     likeStatus =hostHolder.getUser()==null?0: likeService.findEntityLikeStatus(hostHolder.getUser().getId(),ENTITY_TYPE_COMMENT,reply.getId());
                     replyVo.put("likeStatus",likeStatus);


//                     回复的目标
                     System.out.println(reply.getTargetId());
                     User target = reply.getTargetId() == 0 ? null : userService.findUserId(reply.getTargetId());
                     System.out.println(target);
                     replyVo.put("target", target);

                     replyVoList.add(replyVo);
                 }
             }
             commentVo.put("replys",replyVoList);
//             回复数量
             int replyCount = commentService.findCommentCount(ENTITY_TYPE_COMMENT,comment.getId());
             commentVo.put("replyCount",replyCount);

             commentVoList.add(commentVo);
         }
     }

     model.addAttribute("comments",commentVoList);

     return "/site/discuss-detail";
    }

    // 置顶
    @RequestMapping(path = "/top", method = RequestMethod.POST)
    @ResponseBody
    public String setTop(int id) {
        discussPostService.updateType(id, 1);

        // 触发发帖事件
        Event event = new Event()
                .setTopic(TOPIC_PUBLISH)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(ENTITY_TYPE_POST)
                .setEntityId(id);
        eventProducer.fireEvent(event);

        return CommunityUtil.getJSONString(0);
    }

    // 加精
    @RequestMapping(path = "/wonderful", method = RequestMethod.POST)
    @ResponseBody
    public String setWonderful(int id) {
        discussPostService.updateStatus(id, 1);

        // 触发发帖事件
        Event event = new Event()
                .setTopic(TOPIC_PUBLISH)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(ENTITY_TYPE_POST)
                .setEntityId(id);
        eventProducer.fireEvent(event);

        // 计算帖子分数
        String redisKey = RedisKeyUtil.getPostScoreKey();
        redisTemplate.opsForSet().add(redisKey, id);

        return CommunityUtil.getJSONString(0);
    }

    // 删除
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String setDelete(int id) {
        discussPostService.updateStatus(id, 2);

        // 触发删帖事件
        Event event = new Event()
                .setTopic(TOPIC_DELETE)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(ENTITY_TYPE_POST)
                .setEntityId(id);
        eventProducer.fireEvent(event);

        return CommunityUtil.getJSONString(0);
    }

}
