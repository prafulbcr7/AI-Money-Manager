package com.java.aimoneymanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/status", "/health"})
public class HealthController {

    @GetMapping
    public String getHealthStatus() {
        return "Application is running on the machine !!!";
    }
}
