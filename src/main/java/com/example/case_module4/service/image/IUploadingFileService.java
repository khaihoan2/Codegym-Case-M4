package com.example.case_module4.service.image;

import com.example.case_module4.model.UploadingFile;
import com.example.case_module4.model.User;
import com.example.case_module4.service.IGeneralService;

import java.util.Optional;

public interface IUploadingFileService extends IGeneralService<UploadingFile> {
    Optional<UploadingFile> findByUser(User user);

    Iterable<UploadingFile> findByRoomId(Long id);

    Iterable<UploadingFile> findByUserId(Long id);

}
