package com.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-9-7.
 */

public class PublicTest {

    public static void main(String[] args) {
        ConcurrentTask tesk = new ConcurrentTask();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(tesk));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}

class ConcurrentTask implements Runnable {

    BusinessService service;

    public ConcurrentTask() {
        service = new BusinessService();
    }

    @Override
    public void run() {
        service.service();
    }
}

class BusinessService {

    private int sequence = 0;

    public void service() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (""){
            System.out.println(sequence);
            sequence++;
        }
    }
}


