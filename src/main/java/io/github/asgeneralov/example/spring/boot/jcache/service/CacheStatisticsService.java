package io.github.asgeneralov.example.spring.boot.jcache.service;

import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Сервис для получения статистики кешей
 */
@Service
public class CacheStatisticsService {

    private final CacheManager cacheManager;

    public CacheStatisticsService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Получает статистику всех кешей
     */
    public Map<String, Map<String, Object>> getAllCacheStatistics() {
        Map<String, Map<String, Object>> allStats = new HashMap<>();
        
        if (cacheManager instanceof JCacheCacheManager jCacheCacheManager) {
            javax.cache.CacheManager jCacheManager = jCacheCacheManager.getCacheManager();
            if (jCacheManager != null) {
                Set<String> cacheNames = new java.util.HashSet<>();
                for (String name : jCacheManager.getCacheNames()) {
                    cacheNames.add(name);
                }
                
                for (String cacheName : cacheNames) {
                    javax.cache.Cache<?, ?> cache = jCacheManager.getCache(cacheName);
                    if (cache != null) {
                        allStats.put(cacheName, getCacheStatistics(cache));
                    }
                }
            }
        }
        
        return allStats;
    }

    /**
     * Получает статистику конкретного кеша
     */
    public Map<String, Object> getCacheStatistics(String cacheName) {
        if (cacheManager instanceof JCacheCacheManager jCacheCacheManager) {
            javax.cache.CacheManager jCacheManager = jCacheCacheManager.getCacheManager();
            if (jCacheManager != null) {
                javax.cache.Cache<?, ?> cache = jCacheManager.getCache(cacheName);
                
                if (cache != null) {
                    return getCacheStatistics(cache);
                }
            }
        }
        
        return Map.of("error", "Cache not found: " + cacheName);
    }

    /**
     * Получает базовую информацию о кеше
     */
    private Map<String, Object> getCacheStatistics(javax.cache.Cache<?, ?> cache) {

        Map<String, Object> stats = new HashMap<>();
        
        try {
            // Базовая информация о кеше
            stats.put("name", cache.getName());
            stats.put("isClosed", cache.isClosed());
            
            // Попытка получить размер кеша (если доступно)
            try {
                int size = 0;
                for (javax.cache.Cache.Entry<?, ?> entry : cache) {
                    size++;
                }
                stats.put("size", size);
            } catch (Exception e) {
                stats.put("size", "unknown");
            }
            
            // Информация о конфигурации
            try {
                javax.cache.configuration.Configuration<?, ?> config = cache.getConfiguration(javax.cache.configuration.Configuration.class);
                if (config != null) {
                    stats.put("isStoreByValue", config.isStoreByValue());
                }
            } catch (Exception e) {
                stats.put("configError", "Configuration not accessible: " + e.getMessage());
            }
            
            // Статистика недоступна в данной версии JCache
            stats.put("statisticsAvailable", false);
            stats.put("note", "Detailed statistics require javax.cache.stats package which is not available in current JCache implementation");
            
        } catch (Exception e) {
            stats.put("error", "Failed to get cache information: " + e.getMessage());
        }
        
        return stats;
    }

    /**
     * Получает общую информацию о кешах
     */
    public Map<String, Object> getCacheInfo() {
        Map<String, Object> info = new HashMap<>();
        
        if (cacheManager instanceof JCacheCacheManager jCacheCacheManager) {
            javax.cache.CacheManager jCacheManager = jCacheCacheManager.getCacheManager();
            if (jCacheManager != null) {
                Set<String> cacheNames = new java.util.HashSet<>();
                for (String name : jCacheManager.getCacheNames()) {
                    cacheNames.add(name);
                }
                
                info.put("totalCaches", cacheNames.size());
                info.put("cacheNames", cacheNames);
                info.put("cacheManagerClass", jCacheManager.getClass().getSimpleName());
                info.put("cacheManagerName", "JCache Manager");
                try {
                    info.put("cacheManagerURI", jCacheManager.getURI().toString());
                } catch (Exception e) {
                    info.put("cacheManagerURI", "unknown");
                }
            }
        }
        
        return info;
    }

    /**
     * Получает информацию о Spring Cache Manager
     */
    public Map<String, Object> getSpringCacheInfo() {
        Map<String, Object> info = new HashMap<>();
        
        info.put("cacheManagerClass", cacheManager.getClass().getSimpleName());
        info.put("cacheNames", cacheManager.getCacheNames());
        
        return info;
    }
}