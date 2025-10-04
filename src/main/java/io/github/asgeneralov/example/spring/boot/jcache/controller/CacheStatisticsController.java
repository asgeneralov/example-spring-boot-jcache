package io.github.asgeneralov.example.spring.boot.jcache.controller;

import io.github.asgeneralov.example.spring.boot.jcache.service.CacheStatisticsService;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Кастомный endpoint для статистики кешей в Spring Boot Actuator
 */
@Component
@Endpoint(id = "cachestats")
public class CacheStatisticsController {

    private final CacheStatisticsService cacheStatisticsService;

    public CacheStatisticsController(CacheStatisticsService cacheStatisticsService) {
        this.cacheStatisticsService = cacheStatisticsService;
    }

    /**
     * Получает статистику всех кешей
     */
    @ReadOperation
    public Map<String, Object> getAllCacheStatistics() {
        Map<String, Map<String, Object>> allStats = cacheStatisticsService.getAllCacheStatistics();
        Map<String, Object> cacheInfo = cacheStatisticsService.getCacheInfo();
        Map<String, Object> springCacheInfo = cacheStatisticsService.getSpringCacheInfo();
        
        return Map.of(
            "jcacheInfo", cacheInfo,
            "springCacheInfo", springCacheInfo,
            "statistics", allStats
        );
    }

    /**
     * Получает статистику конкретного кеша
     */
    @ReadOperation
    public Map<String, Object> getCacheStatistics(@Selector String cacheName) {
        Map<String, Object> stats = cacheStatisticsService.getCacheStatistics(cacheName);
        return Map.of(
            "cacheName", cacheName,
            "statistics", stats
        );
    }
}
