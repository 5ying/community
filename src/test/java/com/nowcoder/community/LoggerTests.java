package com.nowcoder.community;/*
 *文件名: LoggerTests
 *创建者: wwy
 *创建时间:2022/6/14 11:12
 *描述:
 */

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoggerTests {

    private static final Logger loggger = LoggerFactory.getLogger(LoggerTests.class);

    @Test
    public void testLogger(){
        System.out.println(loggger.getName());
        loggger.debug("debug log");
        loggger.info("info log");
        loggger.warn("warn info ");
        loggger.error("error info");

    }
}
