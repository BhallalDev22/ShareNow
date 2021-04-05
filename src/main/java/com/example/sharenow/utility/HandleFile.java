package com.example.sharenow.utility;

import java.io.File;

public class HandleFile {

    public static void deleteFileFromStorage(String uniqueFileName) {
        String pathname = "/home/aashish/IntelliJ_projects/ShareNow/fileStorage/" + uniqueFileName ;
        // System.out.println(pathname);
        File file = new File(pathname);
        file.delete();
    }


}
