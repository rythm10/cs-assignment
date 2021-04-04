package org.example.log.util;

public class SchedulerUtil extends Thread implements Runnable {


    @Override
    public void run() {

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
