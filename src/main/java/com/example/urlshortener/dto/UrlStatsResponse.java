package com.example.urlshortener.dto;

public class UrlStatsResponse 
{
    private String shortCode;
    private String originalUrl;
    private long clickCount;
    
    public UrlStatsResponse(
        String shortCode,
        String originalUrl,
        long clickCount)
    {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.clickCount = clickCount;
    }

    public String getShortCode()
    {
        return shortCode;
    }

    public String getOriginalUrl()
    {
        return originalUrl;
    }

    public long getClickCount()
    {
        return clickCount;
    }
}
