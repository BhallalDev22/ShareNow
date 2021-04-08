package com.example.sharenow;

import com.example.sharenow.actions.MaintainFileStorage;
import com.example.sharenow.service.ServerSync;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SharenowApplication {


    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SharenowApplication.class, args);

        ServerSync serverSync = applicationContext.getBean(ServerSync.class);
        serverSync.getOldFileIdList();
        serverSync.sync();
        serverSync.updateFilesInStorage();

        Thread t = new Thread(() -> {
            while (true) {
                MaintainFileStorage maintainFileStorage = applicationContext.getBean(MaintainFileStorage.class);
                maintainFileStorage.deleteExtraFiles();
                try {
                    Thread.sleep(3600*1000); // run thread once in an hour
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

}
