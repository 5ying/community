package com.nowcoder.community.entity;/*
 *文件名: SearchResult
 *创建者: wwy
 *创建时间:2022/7/28 11:32
 *描述:
 */

import java.util.List;

/**
 * 自定义实体
 * 用于暂存es中查询到的列表和行数
 */

public class SearchResult {
    private List<DiscussPost> list;
    private long total;
    public SearchResult(List<DiscussPost> list, long total) {
        this.list = list;
        this.total = total;
    }

    public List<DiscussPost> getList() {
        return list;
    }

    public void setList(List<DiscussPost> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
