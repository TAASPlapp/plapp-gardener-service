package com.plappgardenerservice.utils;

import com.plappgardenerservice.services.ScheduleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com.plappgardenerservice.notifier")
public class AppConfig{

    /*
    @Bean
    public ScheduleService getScheduleService() {
        return new ScheduleService();
    }
*/

}
