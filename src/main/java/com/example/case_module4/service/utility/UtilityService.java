package com.example.case_module4.service.utility;

import com.example.case_module4.model.Utility;
import com.example.case_module4.repository.IUtilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UtilityService implements IUtilityService{
    @Autowired
    private IUtilityRepository utilityRepository;

    @Override
    public Iterable<Utility> findAll() {
        return utilityRepository.findAll();
    }

    @Override
    public Optional<Utility> findById(Long id) {
        return utilityRepository.findById(id);
    }

    @Override
    public Utility save(Utility utility) {
        return utilityRepository.save(utility);
    }

    @Override
    public void deleteById(Long id) {
        utilityRepository.deleteById(id);
    }
}
