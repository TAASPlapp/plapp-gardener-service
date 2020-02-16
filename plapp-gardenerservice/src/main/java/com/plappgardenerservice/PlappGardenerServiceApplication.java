package com.plappgardenerservice;

import com.plappgardenerservice.services.ScheduleNotifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlappGardenerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlappGardenerServiceApplication.class, args);
		ScheduleNotifier notifier = new ScheduleNotifier();
		notifier.run();
	}

}
