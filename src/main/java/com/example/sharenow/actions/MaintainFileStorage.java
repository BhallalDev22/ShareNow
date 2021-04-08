package com.example.sharenow.actions;

import com.example.sharenow.entity.FileInfo;
import com.example.sharenow.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.sharenow.utility.HandleFile.deleteFileChunkFromStorage;
import static com.example.sharenow.utility.HandleFile.deleteFileFromStorage;

@Service
public class MaintainFileStorage {

    @Autowired
    private FileRepository fileRepository;

    public List<FileInfo> getFileInfoWithTimeLessThanGivenTime() {
        List<FileInfo> fileInfoList = new ArrayList<>();
        try {
            fileInfoList.addAll(fileRepository.findFileWithTimeLessThanGivenTime());
        }
        catch (Exception e) {
            e.printStackTrace();
            // System.out.println(e.getMessage());
        }
        return fileInfoList;
    }

    public void deleteExtraFiles() {
        List<FileInfo> toBeDeletedFiles = getFileInfoWithTimeLessThanGivenTime();
        for(FileInfo fileInfo : toBeDeletedFiles) {
            deleteFileFromStorage(fileInfo.getFileId() + fileInfo.getType());
            deleteFileChunkFromStorage(fileInfo.getFileId() + "_1");
            deleteFileChunkFromStorage(fileInfo.getFileId() + "_2");
            //System.out.println(fileInfo.getFileId());
        }
        fileRepository.deleteFileWithTimeLessThanGivenTime();
    }
}
