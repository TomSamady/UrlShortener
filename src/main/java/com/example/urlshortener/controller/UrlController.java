package com.example.urlshortener.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.urlshortener.service.URLservice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.net.URI;

@RestController 
public class UrlController 
{
    private final URLservice urlService;

    public UrlController(URLservice urlService)
    {
        this.urlService = urlService;
    }
       
        //Depedancy Injection -- Spring sees @Service and passes the service to the controller.

    @PostMapping("/shorten")
    public String shortenURL(@RequestBody String originalURL)
    {
        String shortCode = urlService.shortenUrl(originalURL);

        return "https://localhost:8080/" + shortCode;
    }

}

