package com.example.urlshortener.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.net.URI;
import java.net.URISyntaxException;

@Service 
public class URLservice 
{
    private final Map<String, String> urls = new HashMap<>();

    public String shortenUrl(String originalURL)
    {
        String shortCode = UUID.randomUUID() // gives long UUID code
            .toString()
            .substring(0, 6); // takes first 6 characters of UUID

        urls.put(shortCode, originalURL); //stores URL and shortened in hashMap
        return shortCode;
    }

    public String getOriginalUrl(String shortCode)
    {
        return urls.get(shortCode);
    }

    public boolean isValidUrl(String url)
    {
        try
        {
        URI uri = new URI(url);

        String scheme = uri.getScheme();

        boolean validScheme = scheme != null && (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"));

        boolean hasHost = uri.getHost() != null;

        return validScheme && hasHost;
        }
        catch(URISyntaxException e)
        {
        return false;
        }
    
    }
}
    

