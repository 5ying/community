package com.nowcoder.community.dao;/*
 *文件名: CommentMapper
 *创建者: wwy
 *创建时间:2022/7/2 22:04
 *描述: 评论的数据层
 */

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);

    Comment selectCommentById(int id);


}
