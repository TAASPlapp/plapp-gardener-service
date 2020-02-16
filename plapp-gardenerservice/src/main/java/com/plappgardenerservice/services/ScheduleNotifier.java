package com.plappgardenerservice.services;

import java.util.Timer;


/* This class implements a thread whose task is to periodically notify users
 * which activities they should perform onto their plants (watering, pruning, etc).
 */
public class ScheduleNotifier implements Runnable {

    long delay = 1800000; //half an hour

    @Override
    public void run() {
        while(true){
            //TODO check if some plant ops should be notified and notify
            //sleep for half an hour
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
