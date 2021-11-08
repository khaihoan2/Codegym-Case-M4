package com.example.case_module4.service.subscriber;

import com.example.case_module4.model.Subscriber;
import com.example.case_module4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISubscriberService extends IGeneralService<Subscriber> {
    Page<Subscriber> findAll(Pageable pageable);
}
