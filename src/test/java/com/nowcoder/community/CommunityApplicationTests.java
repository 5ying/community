package com.nowcoder.community;

import com.nowcoder.community.controller.AlphaController;
import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext; // 获取当前Bean工厂容器
	}

	@Test
	public void testApplicationContext(){
		System.out.println(applicationContext);

//		调用接口，会直接调用实现接口类的第一个类
		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());

//		通过实现类的名字调用，就是可以实现定向调用
		alphaDao = applicationContext.getBean("alphaHibernate",AlphaDao.class);
		System.out.println(alphaDao.select());

	}

	@Test
	public void testBeanManagement(){
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);


	}

//	通过依赖注入（注解）的方式，进行获取Bean
	@Autowired
	@Qualifier("alphaHibernate") // 一个接口下面有多个实例类，通过名字指定将哪个实例类加载到bean中
	private AlphaDao alphaDao;

	@Autowired
	private AlphaController alphaController;

	@Test
	public void tetDI(){

		System.out.println(alphaDao);
//		System.out.println(alphaController.http(););
	}





}
