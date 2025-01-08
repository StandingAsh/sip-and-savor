package com.project.demo.domain.whiskeys.controller;

import com.project.demo.domain.whiskeys.service.WhiskeyCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class WhiskeyCrawlingController {

    private final WhiskeyCrawlingService whiskeyCrawlingService;

    @GetMapping("/crawling")
    public void crawling() {
        whiskeyCrawlingService.crawl();
    }
}
