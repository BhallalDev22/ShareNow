package com.example.sharenow;

import com.example.sharenow.actions.MaintainFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SharenowApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharenowApplication.class, args);

        Thread t = new Thread(() -> {
            while (true) {
                new MaintainFileStorage().deleteExtraFiles();
                try {
                    Thread.sleep(24*60*60*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

    }

}
