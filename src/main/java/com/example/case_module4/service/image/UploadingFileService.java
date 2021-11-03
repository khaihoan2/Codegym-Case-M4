package com.example.case_module4.service.image;

import com.example.case_module4.model.UploadingFile;
import com.example.case_module4.model.Review;
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
    public Optional<Review> deleteById(Long id) {
        uploadingFileRepository.deleteById(id);
        return null;
    }

    @Override
    public Optional<UploadingFile> findByUser(User user) {
        return uploadingFileRepository.findByUser(user);
    }
}
