package com.plappgardenerservice.services;

import java.util.Timer;


/* This class implements a thread whose task is to periodically notify users about
 * which activities they should perform onto their plants (watering, pruning, etc).
 */
public class ScheduleNotifier implements Runnable {

    long delay = 1800; //sleep time in ms

    @Override
    public void run() {
        while(true){
            //System.out.println("Schedule Notifier is online");
            //TODO check if some plant ops should be notified and notify

            //take a nap
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
