package com.example.urlshortener.controller;

import com.example.urlshortener.dto.shortenRequest;
import com.example.urlshortener.dto.shortenResponse;
import com.example.urlshortener.service.URLservice;
import com.example.urlshortener.dto.UrlStatsResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;


@RestController 
public class UrlController 
{
    private final URLservice urlService;
    private final String baseUrl;

    public UrlController(URLservice urlService, @Value("${app.base-url}") String baseUrl)
    {
        this.urlService = urlService;
        this.baseUrl = baseUrl;
    }
       
        //Depedancy Injection -- Spring sees @Service and passes the service to the controller.

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenURL(@RequestBody shortenRequest request)
    {
        String originalUrl = request.getUrl();

        if(!urlService.isValidUrl((originalUrl)))
        {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid URL, please enter a valid http or https URL");
        }

        String shortCode = urlService.shortenUrl(originalUrl);

        String shortUrl = baseUrl + "/" + shortCode;

        return ResponseEntity.ok(
            new shortenResponse(originalUrl, shortUrl));
    }

    @GetMapping("/{shortCode:[A-Za-z0-9]+}")
    public ResponseEntity<Void> redirect(
        @PathVariable("shortCode") String shortCode)
        {
            String originalUrl = urlService.getOriginalUrl(shortCode);
            
            if(originalUrl == null)
            {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(302).location(URI.create(originalUrl)).build();
        }

    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<?> getStats(@PathVariable String shortCode) 
    {
        UrlStatsResponse stats = urlService.getStats(shortCode);
            
        if(stats == null)
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(stats);
    }
        
}

