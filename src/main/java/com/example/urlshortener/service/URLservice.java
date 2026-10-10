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
    private final Map<String, String> urlToCode = new HashMap<>();

    public String shortenUrl(String originalURL)
    {
        if(urlToCode.containsKey(originalURL))
        {
            return urlToCode.get(originalURL); //Checks if a code for the url already exists.
        }
        
        String shortCode;

        do //Handles the case where the substring generates the same code.
        {
            shortCode = UUID.randomUUID() // gives long UUID code
                .toString()
                .substring(0, 6); // takes first 6 characters of UUID
        }
        while(urls.containsKey(shortCode));

        urls.put(shortCode, originalURL); //stores URL and shortened in hashMap
        urlToCode.put(originalURL, shortCode); //stores URLs opposite format; allows checking...
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
    

