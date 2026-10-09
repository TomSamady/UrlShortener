package com.example.urlshortener.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.dto.shortenRequest;
import com.example.urlshortener.dto.shortenResponse;
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

        String shortUrl = "http://localhost:8080/" + shortCode;

        return ResponseEntity.ok(
            new shortenResponse(originalUrl, shortUrl));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
        @PathVariable String shortCode)
        {
            String originalUrl = urlService.getOriginalUrl(shortCode);
            
            if(originalUrl == null)
            {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(302).location(URI.create(originalUrl)).build();
        }
}

