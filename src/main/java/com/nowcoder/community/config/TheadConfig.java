package com.nowcoder.community.config;/*
 *文件名: TheadConfig
 *创建者: wwy
 *创建时间:2022/7/30 17:57
 *描述:
 */


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
public class TheadConfig {
}
