package com.nowcoder.community.controller;/*
 *文件名: AlphaController
 *创建者: wwy
 *创建时间:2022/5/30 22:21
 *描述:
 */

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String saveHello(){
        return  "Hello Spring Boot.";
    }


    @RequestMapping("/data")
    @ResponseBody
    public String getdata(){
        return alphaService.find();
    }


//  怎么获得请求对象
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
//        获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
//        迭代器
        Enumeration enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name =(String)enumeration.nextElement();
            String vale = request.getHeader(name);
            System.out.println(name+": "+vale);
        }
        System.out.println(request.getParameter("code"));
//        返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try(PrintWriter writer = response.getWriter();) {
            writer.write("<hr>牛客</hr>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    接受请求数据 怎么接受请求数据
//    GET
    /*在get请求方式中，有两种传参的方式：一种是？拼接，一种是将参数放到路径中*/
//     /students?current =1& limit =20
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@RequestParam(name = "current",required = false,defaultValue = "10") int current, @RequestParam(name = "limit",required=false,defaultValue = "1") int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }
//  /student/123
@RequestMapping(path = "/students/{id}",method = RequestMethod.GET)
@ResponseBody
public String getStudent(@PathVariable("id")int id){
    System.out.println(id);
    return "a students";
}


//POST请求
    @RequestMapping(path = "/student",method =  RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

//    如何像浏览器返回响应数据
//    响应动态页面
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age","30");
        mav.setViewName("/demo/view");
        return mav;
    }
//    这是一种比较简单的方式 比较常用的方式
    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","北京大学" );
        model.addAttribute("age","30");
        return "/demo/view";
    }

//   响应JSON数据 什么时候响应呢？异步请求
//    java对象 -> JSON -> js对象
    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEMp(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age","30");
        map.put("salary","8000.00");
        return map;
    }

//集合
    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEMps(){
       List <Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age","30");
        map.put("salary","8000.00");
        list.add(map);


        return list;
    }

}
