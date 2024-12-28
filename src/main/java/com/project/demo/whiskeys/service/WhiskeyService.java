package com.project.demo.whiskeys.service;

import com.project.demo.whiskeys.entity.Whiskey;
import com.project.demo.whiskeys.repository.WhiskeyRepository;
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
