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

/**
 * Конфигурация кеширования для приложения
 */
@Configuration
public class CacheConfig {

    @Bean
    public JCacheCacheManager cacheManager() {
        // Получаем JCache провайдер
        CachingProvider cachingProvider = Caching.getCachingProvider();
        
        // Создаем JCache менеджер
        javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager();

        // Создаем кеши программно через JCache API
        cacheManager.createCache("helloCache", 
            new javax.cache.configuration.MutableConfiguration<String, String>()
                .setTypes(String.class, String.class)
                .setStoreByValue(true)
                .setExpiryPolicyFactory(
                    CreatedExpiryPolicy.factoryOf(Duration.FIVE_MINUTES))
                .setStatisticsEnabled(true)
        );
 
        return new JCacheCacheManager(cacheManager);
    }

}
