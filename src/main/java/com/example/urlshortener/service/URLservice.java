package com.example.urlshortener.service;

import org.springframework.stereotype.Service;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.model.UrlMapping;
import java.util.UUID;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Optional;

@Service 
public class URLservice 
{
    private final UrlRepository urlRepository;

    public URLservice(UrlRepository urlRepository)
    {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalURL)
    {
        String normalisedURL = normaliseString(originalURL);

        Optional<UrlMapping> existing = urlRepository.findByOriginalUrl(normalisedURL);

        if(existing.isPresent())
        {
            return existing.get().getShortCode();
        }
        
        String shortCode;

        do //Handles the case where the substring generates the same code.
        {
            shortCode = UUID.randomUUID() // gives long UUID code
                .toString()
                .substring(0, 6); // takes first 6 characters of UUID
        }
        while(urlRepository.existsByShortCode(shortCode));

        UrlMapping mapping = new UrlMapping(shortCode, normalisedURL);
        urlRepository.save(mapping);
        return shortCode;
    }

    public String getOriginalUrl(String shortCode)
    {
        Optional<UrlMapping> mapping = urlRepository.findByShortCode(shortCode);

        if(mapping.isPresent())
        {
            return mapping.get().getOriginalUrl();
        }

        return null;
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
    

