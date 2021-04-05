package com.example.sharenow.repository;

import com.example.sharenow.entity.FileInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<FileInfo,String> {

    @Query(value = "SELECT * FROM file_info f WHERE (SELECT ADDTIME(f.timestamp, f.no_of_hours * 3600) < ?1" , nativeQuery = true)
    List<FileInfo> findFileWithTimeLessThanGivenTime(Timestamp timestamp);
}
