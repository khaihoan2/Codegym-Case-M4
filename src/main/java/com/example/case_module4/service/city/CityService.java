package com.example.case_module4.service.city;

import com.example.case_module4.model.City;
import com.example.case_module4.repository.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CityService implements ICityService{

    @Autowired
    private ICityRepository cityRepository;

    @Override
    public Iterable<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }
}
