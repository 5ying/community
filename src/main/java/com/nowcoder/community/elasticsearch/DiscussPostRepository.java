package com.nowcoder.community.elasticsearch;/*
 *文件名: DiscussPostRepository
 *创建者: wwy
 *创建时间:2022/7/27 16:31
 *描述:
 */

import com.nowcoder.community.entity.DiscussPost;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

@Configuration
//开启扫描搜索引擎的注解

public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {
}
