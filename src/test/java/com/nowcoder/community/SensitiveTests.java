package com.nowcoder.community;/*
 *文件名: SensitiveTests
 *创建者: wwy
 *创建时间:2022/6/30 21:23
 *描述:
 */

import com.nowcoder.community.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = CommunityApplication.class)
@SpringBootTest
public class SensitiveTests {
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Test
    public void testSensitiveFilter(){
        String test ="这里可以赌博,可以开票，哈哈哈";
        test = sensitiveFilter.filter(test);
        System.out.println(test);
    }
}
