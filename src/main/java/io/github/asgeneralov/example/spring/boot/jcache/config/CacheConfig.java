package io.github.asgeneralov.example.spring.boot.jcache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.Iterator;

/**
 * Конфигурация кеширования для приложения
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // Получаем JCache провайдер
        CachingProvider cachingProvider = Caching.getCachingProvider();
        
        // Создаем JCache менеджер
        javax.cache.CacheManager jcacheManager = cachingProvider.getCacheManager();


        // Создаем кеши программно через JCache API
        if (!cacheExists(jcacheManager, "helloCache")) {
            jcacheManager.createCache("helloCache", 
                new javax.cache.configuration.MutableConfiguration<String, String>()
                    .setTypes(String.class, String.class)
                    .setStoreByValue(false)
                    .setExpiryPolicyFactory(
                        CreatedExpiryPolicy.factoryOf(Duration.FIVE_MINUTES))
                    .setStatisticsEnabled(true)
            );
        }

        return new JCacheCacheManager(jcacheManager);
    }

    private boolean cacheExists(javax.cache.CacheManager cacheManager, String cacheName) {
        Iterator<String> iterator = cacheManager.getCacheNames().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(cacheName)) {
                return true;
            }
        }
        return false;
    }


}
