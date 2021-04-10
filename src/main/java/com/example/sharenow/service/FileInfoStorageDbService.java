package com.example.sharenow.service;

import com.example.sharenow.entity.FileInfo;
import com.example.sharenow.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.example.sharenow.utility.HandleFile.deleteFileFromStorage;
import static com.example.sharenow.utility.TimeStampOperations.calculateTimeStamp;
import static com.example.sharenow.utility.TimeStampOperations.compareWithCurrentTimeStamp;

@Service
public class FileInfoStorageDbService {

    @Autowired
    private FileRepository fileRepository;

    public List<FileInfo> getAllFileInfo() {
        // return all file info
        List<FileInfo> fileInfoList = new ArrayList<>();
        fileRepository.findAll().
                forEach(fileInfoList::add);
        return fileInfoList;
    }

    public List<FileInfo> getUserFileInfo(String ownerName) {
        List<FileInfo> fileInfoList = new ArrayList<>(fileRepository.findByOwnerName(ownerName));
        return fileInfoList;
    }

    public void addFileInfo(FileInfo fileInfo) {
        fileRepository.save(fileInfo);
    }

    public boolean canBeDownloaded(String fileId) {
        if( fileRepository.existsById(fileId) ) {
            FileInfo fileInfo = fileRepository.findById(fileId).get();
            if(fileInfo.getNoOfTimes() > 0) {
                Timestamp timestamp = fileInfo.getUploadTimestamp();
                int hrs = fileInfo.getNoOfHours();
                Timestamp toBeDeleted = calculateTimeStamp(timestamp,hrs);
                if(compareWithCurrentTimeStamp(toBeDeleted))
                    return true;
                else
                    deleteFile(fileId,fileInfo.getType());
            }
        }
        return false;
    }

    public void updateNoOfTimes(String fileId) {
        FileInfo fileInfo = fileRepository.findById(fileId).get();
        fileInfo.setNoOfTimes(fileInfo.getNoOfTimes()-1);
        fileRepository.save(fileInfo);
        if(fileInfo.getNoOfTimes() == 0) {
            deleteFile(fileId,fileInfo.getType());
        }
    }

    public void deleteFile(String fileId,String type) {
        String uniqueFileName = fileId + type;
        deleteFileFromStorage(uniqueFileName);
        fileRepository.deleteById(fileId);
    }

    public String findType(String fileId) {
        return fileRepository.findById(fileId).get().getType();
    }
}
