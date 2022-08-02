package com.nowcoder.community.quartz;/*
 *文件名: AlphaJob
 *创建者: wwy
 *创建时间:2022/7/30 21:05
 *描述:
 */

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AlphaJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName() + ": execute a quartz job.");
    }

}
