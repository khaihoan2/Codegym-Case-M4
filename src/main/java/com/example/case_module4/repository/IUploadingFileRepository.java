package com.example.case_module4.repository;

import com.example.case_module4.model.UploadingFile;
import com.example.case_module4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUploadingFileRepository extends JpaRepository<UploadingFile, Long> {
    Optional<UploadingFile> findByUser(User user);
}
