package com.nowcoder.community.annotation;/*
 *文件名: LoginRequired
 *创建者: wwy
 *创建时间:2022/6/21 17:12
 *描述:
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
}
