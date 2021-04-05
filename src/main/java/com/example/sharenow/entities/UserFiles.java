package com.example.sharenow.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "UserFiles", schema = "ShareNow")
public class UserFiles {

    @Id
    @Column(name = "FileID", length = 100)
    private String FileID;

    @Column(name = "FileName", length = 100)
    private String FileName;

    @Column(name = "OwnerName", length = 100)
    private String OwnerName;

    @Column(name = "NoOfTimes")
    private int NoOfTimes;

    @Column(name="UploadTimeStamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp UploadTimeStamp;

    @Column(name = "NoOfHours")
    private int NoOfHours;

    @Column(name = "Size")
    private Long Size;

    @Column(name = "Type", length = 50)
    private String Type;

    public String getFileID() {
        return FileID;
    }

    public void setFileID(String fileID) {
        FileID = fileID;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public int getNoOfTimes() {
        return NoOfTimes;
    }

    public void setNoOfTimes(int noOfTimes) {
        NoOfTimes = noOfTimes;
    }

    public Timestamp getUploadTimeStamp() {
        return UploadTimeStamp;
    }

    public void setUploadTimeStamp(Timestamp uploadTimeStamp) {
        UploadTimeStamp = uploadTimeStamp;
    }

    public int getNoOfHours() {
        return NoOfHours;
    }

    public void setNoOfHours(int noOfHours) {
        NoOfHours = noOfHours;
    }

    public Long getSize() {
        return Size;
    }

    public void setSize(Long size) {
        Size = size;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
