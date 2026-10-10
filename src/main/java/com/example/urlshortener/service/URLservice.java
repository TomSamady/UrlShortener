package com.example.urlshortener.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

@Service 
public class URLservice 
{
    private final Map<String, String> urls = new HashMap<>();
    private final Map<String, String> urlToCode = new HashMap<>();

    public String shortenUrl(String originalURL)
    {
        String normalisedURL = normaliseString(originalURL);

        if(urlToCode.containsKey(normalisedURL))
        {
            return urlToCode.get(normalisedURL); //Checks if a code for the url already exists.
        }
        
        String shortCode;

        do //Handles the case where the substring generates the same code.
        {
            shortCode = UUID.randomUUID() // gives long UUID code
                .toString()
                .substring(0, 6); // takes first 6 characters of UUID
        }
        while(urls.containsKey(shortCode));

        urls.put(shortCode, normalisedURL); //stores URL and shortened in hashMap
        urlToCode.put(normalisedURL, shortCode); //stores URLs opposite format; allows checking...
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

    public String normaliseString(String url)
    {
        try{
            URI uri = new URI(url.trim()).normalize();

            String scheme = uri.getScheme().toLowerCase(Locale.ROOT);
            String host = uri.getHost().toLowerCase(Locale.ROOT);

            int port = uri.getPort();

            if((scheme.equals("htpp") && port == 80) || scheme.equals("https") && port == 443)
            {
                port = -1;
            }

            String path = uri.getPath();

            if(path == null || path.isEmpty())
            {
                path = "/";
            }

            URI normalisedURI = new URI(scheme, uri.getUserInfo(), host, port, path, uri.getQuery(), uri.getFragment());

            return normalisedURI.toString();
        }
        catch(URISyntaxException e)
        {
            throw new IllegalArgumentException("Invalid URL");
        }
    }
}
    

