package com.example.urlshortener.repository;

import com.example.urlshortener.model.UrlMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping, Long>
{
    Optional<UrlMapping> findByShortCode(String shortCode);

    Optional<UrlMapping> findByOriginalUrl(String originalUrl);

    boolean existsByShortCode(String shortCode);

    @Modifying 
    @Query("""
        UPDATE UrlMapping u
        SET u.clickCount = u.clickCount + 1
        WHERE u.shortCode = :shortCode
        """)
    int incrementClickCount(
        @Param("shortCode") String shortCode);
}
