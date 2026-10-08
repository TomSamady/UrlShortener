package com.example.urlshortener.dto;

public class shortenResponse 
{
    private String originalUrl;
    private String shortUrl;
    
    public shortenResponse(String originalUrl, String shortUrl)
    {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl()
    {
        return originalUrl;
    }

    public String getShortUrl()
    {
        return shortUrl;
    }
}
