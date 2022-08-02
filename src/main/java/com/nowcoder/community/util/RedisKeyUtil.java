package com.nowcoder.community.util;/*
 *文件名: RedisKeyUtil
 *创建者: wwy
 *创建时间:2022/7/25 10:35
 *描述:
 */

public class RedisKeyUtil {

    private static final String SPLIT =":";
    private static final String PREFIX_ENTITY_LIKE ="like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREIX_FOLLOWEE = "followee";
    private static final String PREIX_FOLLOWER = "follower";
    private static final String PREFIX_KATPTCHA = "katpcha";
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_USER = "user";
    private static final String PREFIX_UV = "uv";
    private static final String PREFIX_DAU = "dau";
    private static final String PREFIX_POST = "post";


//    某个实体的赞
    public static String getEntityLikeKey(int entityType,int entityId){
        return PREFIX_ENTITY_LIKE + SPLIT +entityType+SPLIT+entityId;
    }
//    某个用户的赞
    public static String getUserLikeKey(int userId){
        return PREFIX_USER_LIKE+SPLIT+userId;
    }
//    某个用户关注的实体
    public static String getFolloweeKey(int userId,int entityType){
        return PREIX_FOLLOWEE +SPLIT +userId+SPLIT+entityType;
    }
//   某个用户拥有的粉丝
    public static String getFollowerKey(int entityType,int entityId){
        return PREIX_FOLLOWER+SPLIT+entityType +SPLIT+entityId;
    }
//    登录验证码
    public static String getKaptchaKey(String owner){
        return PREFIX_KATPTCHA + SPLIT + owner;
    }
//    登陆凭证
    public static String getTicketKey(String ticket){
    return PREFIX_TICKET + SPLIT + ticket;
}
//
    public static String getUsertKey(int userId){
    return PREFIX_USER + SPLIT + userId;
}

    // 单日UV
    public static String getUVKey(String date) {
        return PREFIX_UV + SPLIT + date;
    }

    // 区间UV
    public static String getUVKey(String startDate, String endDate) {
        return PREFIX_UV + SPLIT + startDate + SPLIT + endDate;
    }

    // 单日活跃用户
    public static String getDAUKey(String date) {
        return PREFIX_DAU + SPLIT + date;
    }

    // 区间活跃用户
    public static String getDAUKey(String startDate, String endDate) {
        return PREFIX_DAU + SPLIT + startDate + SPLIT + endDate;
    }
    // 帖子分数
    public static String getPostScoreKey() {
        return PREFIX_POST + SPLIT + "score";
    }
}
