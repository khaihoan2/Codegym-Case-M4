package com.example.case_module4.service.image;

import com.example.case_module4.model.Image;
import com.example.case_module4.model.Review;
import com.example.case_module4.repository.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService implements IImageService {

    @Autowired
    private IImageRepository imageRepository;

    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Optional<Review> deleteById(Long id) {
        imageRepository.deleteById(id);
        return null;
    }
}
