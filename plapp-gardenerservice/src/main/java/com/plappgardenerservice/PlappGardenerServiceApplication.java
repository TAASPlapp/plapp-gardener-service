package com.plappgardenerservice;

import com.plappgardenerservice.notifier.ScheduleNotifier;
import com.plappgardenerservice.utils.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication
public class PlappGardenerServiceApplication {

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(PlappGardenerServiceApplication.class, args);
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		ScheduleNotifier sn = (ScheduleNotifier) ctx.getBean("scheduleNotifier");
		sn.start();



	}

}
