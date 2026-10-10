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

    public UrlMapping()
    {

    }

    public UrlMapping(String shortCode, String originalUrl)
    {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
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
}
