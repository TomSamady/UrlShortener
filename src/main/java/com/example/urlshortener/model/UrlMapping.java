package com.example.urlshortener.model;

import jakarta.persistence.*;

//Class represents 1 row of the database

@Entity 
@Table(name = "url_mappings")
public class UrlMapping 
{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String shortCode;

    @Column(nullable = false, unique = true, length = 2048)
    private String originalUrl;

    @Column(nullable = false)
    private long clickCount = 0;

    public UrlMapping()
    {

    }

    public UrlMapping(String shortCode, String originalUrl)
    {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.clickCount = 0;
    }

    public Long getId()
    {
        return id;
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
