package com.example.case_module4.service.subscriber;

import com.example.case_module4.model.Subscriber;
import com.example.case_module4.repository.ISubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriberService implements ISubscriberService{

    @Autowired
    private ISubscriberRepository subscriberRepository;

    @Override
    public Iterable<Subscriber> findAll() {
        return subscriberRepository.findAll();
    }

    @Override
    public Optional<Subscriber> findById(Long id) {
        return subscriberRepository.findById(id);
    }

    @Override
    public Subscriber save(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    @Override
    public void deleteById(Long id) {
        subscriberRepository.deleteById(id);
    }

    @Override
    public Page<Subscriber> findAll(Pageable pageable) {
        return subscriberRepository.findAll(pageable);
    }
}
