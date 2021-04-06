package com.example.sharenow.actions;

import com.example.sharenow.entity.FileInfo;
import com.example.sharenow.repository.FileRepository;
import com.example.sharenow.service.FileInfoStorageDbService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.example.sharenow.utility.HandleFile.deleteFileFromStorage;

public class MaintainFileStorage {

    @Autowired
    private FileRepository fileRepository;

    public List<FileInfo> getFileInfoWithTimeLessThanGivenTime(Timestamp timestamp) {
        List<FileInfo> fileInfoList = new ArrayList<>();
        try {
            fileInfoList.addAll(fileRepository.findFileWithTimeLessThanGivenTime(timestamp));
        }
        catch (Exception e) {
            e.printStackTrace();
            // System.out.println(e.getMessage());
        }
        return fileInfoList;
    }

    public void deleteExtraFiles() {
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        List<FileInfo> toBeDeletedFiles = getFileInfoWithTimeLessThanGivenTime(currentTimeStamp);
        for(FileInfo fileInfo : toBeDeletedFiles) {
            deleteFileFromStorage(fileInfo.getFileId() + fileInfo.getType());
            fileRepository.deleteById(fileInfo.getFileId());
        }
    }
}
