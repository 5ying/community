package com.nowcoder.community.config;/*
 *文件名: AlphaConfig
 *创建者: wwy
 *创建时间:2022/5/31 16:15
 *描述:
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

//配置文件
@Configuration
public class AlphaConfig {

    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
    }

}
