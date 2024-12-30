package com.project.demo.domain.whiskeys.service;

import com.project.demo.domain.whiskeys.repository.WhiskeyRepository;
import com.project.demo.domain.whiskeys.entity.Whiskey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhiskeyService {

    @Autowired
    private WhiskeyRepository whiskeyRepository;

    public List<Whiskey> findAllWhiskeys() {
        return whiskeyRepository.findAll();
    }

    public Whiskey getWhiskeyById(Long id) {
        return whiskeyRepository.findById(id).orElse(null);
    }
}
