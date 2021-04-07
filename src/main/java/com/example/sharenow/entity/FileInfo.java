package com.example.sharenow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class FileInfo {

    @Id
    @Column(length = 100)
    private String fileId;

    @Column(length = 100)
    private String ownerName;

    @Column(length = 100)
    private String fileName;

    private long size;

    @Column(length = 100)
    private String type;

    @Column(columnDefinition = "integer default 1")
    private int noOfTimes;
    @Column(columnDefinition = "integer default 1")
    private int noOfHours;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Timestamp uploadTimestamp;

    public FileInfo() {
    }

    public FileInfo(String fileId, String ownerName, String fileName, long size, String type, int noOfTimes, int noOfHours, Timestamp uploadTimestamp) {
        this.fileId = fileId;
        this.ownerName = ownerName;
        this.fileName = fileName;
        this.size = size;
        this.type = type;
        this.noOfTimes = noOfTimes;
        this.noOfHours = noOfHours;
        this.uploadTimestamp = uploadTimestamp;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNoOfTimes() {
        return noOfTimes;
    }

    public void setNoOfTimes(int noOfTimes) {
        this.noOfTimes = noOfTimes;
    }

    public int getNoOfHours() {
        return noOfHours;
    }

    public void setNoOfHours(int noOfHours) {
        this.noOfHours = noOfHours;
    }

    public Timestamp getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(Timestamp uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }
}
