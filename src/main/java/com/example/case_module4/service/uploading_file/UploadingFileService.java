package com.example.case_module4.service.uploading_file;

import com.example.case_module4.model.Image;
import com.example.case_module4.model.UploadingFile;
import com.example.case_module4.model.User;
import com.example.case_module4.repository.IUploadingFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UploadingFileService implements IUploadingFileService {

    @Autowired
    private IUploadingFileRepository uploadingFileRepository;

    @Override
    public Iterable<UploadingFile> findAll() {
        return uploadingFileRepository.findAll();
    }

    @Override
    public Optional<UploadingFile> findById(Long id) {
        return uploadingFileRepository.findById(id);
    }

    @Override
    public UploadingFile save(UploadingFile uploadingFile) {
        return uploadingFileRepository.save(uploadingFile);
    }

    @Override
    public void deleteById(Long id) {
        uploadingFileRepository.deleteById(id);
    }

    @Override
    public Optional<UploadingFile> findByUser(User user) {
        return uploadingFileRepository.findByUser(user);
    }

    @Override
    public Iterable<UploadingFile> findByRoomId(Long id) {
        return uploadingFileRepository.findByRoomId(id);
    }

    @Override
    public Image findByUserId(Long id) {
        Iterable<UploadingFile> uploadingFiles = uploadingFileRepository.findByUserId(id);
        Image imageOne = new Image();
        for (UploadingFile uploadingFile : uploadingFiles) {
            imageOne.setId(uploadingFile.getId());
            imageOne.setName(uploadingFile.getName());
            break;
        }
        return imageOne;
    }
}
