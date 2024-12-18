package com.portfolio.cms.experience;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/experience")
public class ExperienceController {

    @GetMapping
    public String experience() {
        return "experience";
    }
}
