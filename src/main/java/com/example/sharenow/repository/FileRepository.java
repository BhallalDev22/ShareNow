package com.example.sharenow.repository;

import com.example.sharenow.entity.FileInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<FileInfo,String> {

    List<FileInfo> findByOwnerName(String ownerName);

    @Query(value = "SELECT * FROM file_info f WHERE (SELECT TIMESTAMPADD(SECOND, f.no_of_hours * 3600, f.upload_timestamp)) < CURRENT_TIMESTAMP", nativeQuery = true)
    List<FileInfo> findFileWithTimeLessThanGivenTime();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM file_info WHERE file_info.file_id IN (SELECT file_id FROM file_info f WHERE (SELECT TIMESTAMPADD(SECOND, f.no_of_hours * 3600, f.upload_timestamp)) < CURRENT_TIMESTAMP )", nativeQuery = true)
    void deleteFileWithTimeLessThanGivenTime();

    @Query(value = "SELECT file_id FROM file_info", nativeQuery = true)
    List<String> findFileId();

}
