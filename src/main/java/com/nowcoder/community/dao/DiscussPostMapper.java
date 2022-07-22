package com.nowcoder.community.dao;/*
 *文件名: DiscussPostMapper
 *创建者: wwy
 *创建时间:2022/6/8 11:07
 *描述: 社区首页开发 (显示帖子的首页)
 */

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);

//    @Param 是给 形参起一个别名
//    如果当前方法只有一个参数,并且在<if>里使用,则必须加别名
    int selectDiscussPostRows (@Param("userId") int userId);

    int insertDiscussPostRows(DiscussPost post);

    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);

    int updateType(int id, int type);

    int updateStatus(int id, int status);

    int updateScore(int id, double score);


}
