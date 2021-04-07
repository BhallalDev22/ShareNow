package com.example.sharenow;

import com.example.sharenow.actions.MaintainFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SharenowApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SharenowApplication.class, args);

        Thread t = new Thread(() -> {
            while (true) {
                MaintainFileStorage maintainFileStorage = applicationContext.getBean(MaintainFileStorage.class);
                maintainFileStorage.deleteExtraFiles();
                try {
                    Thread.sleep(20*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

    }

}
