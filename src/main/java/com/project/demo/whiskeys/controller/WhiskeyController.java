package com.project.demo.whiskeys.controller;

import com.project.demo.whiskeys.entity.Whiskey;
import com.project.demo.whiskeys.service.WhiskeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class WhiskeyController {

    @Autowired
    private WhiskeyService whiskeyService;

    @GetMapping("/whiskeys")
    public String display(Model model) {

        List<Whiskey> whiskeyList = whiskeyService.findAllWhiskeys();
        model.addAttribute("whiskeyList", whiskeyList);

        return "whiskeys/whiskeyList";
    }

    @GetMapping("/whiskeys/{id}")
    public String displayDetails(@PathVariable Long id, Model model) {
        model.addAttribute("whiskey", whiskeyService.getWhiskeysById(id));
        return "whiskeys/whiskeyDetail";
    }
}
