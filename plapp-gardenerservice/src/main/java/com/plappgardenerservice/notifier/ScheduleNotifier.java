package com.plappgardenerservice.notifier;

import com.plappgardenerservice.entities.Schedule;
import com.plappgardenerservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Timer;


/* This class implements a thread whose task is to periodically notify users about
 * which activities they should perform onto their plants (watering, pruning, etc).
 */
@Component
@Scope("prototype")
public class ScheduleNotifier extends Thread {

    private long delay = 5000; //sleep time in ms

    @Override
    public void run() {
        while(true){
            System.out.println("Schedule Notifier is online");
            //TODO check if some plant ops should be notified and notify
            ScheduleService ss = new ScheduleService();
            ss.init();
            ss.findAll();
            //take a nap
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
