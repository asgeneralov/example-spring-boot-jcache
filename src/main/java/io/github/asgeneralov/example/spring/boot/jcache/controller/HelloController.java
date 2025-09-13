package io.github.asgeneralov.example.spring.boot.jcache.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @Cacheable(value = "helloCache", key = "#name")
    public String hello(@RequestParam(value = "name", defaultValue = "Мир") String name) {
        // Имитация медленной операции
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return String.format("Привет, %s! (время: %s)", name, System.currentTimeMillis());
    }

    @GetMapping("/cache/clear")
    @CacheEvict(value = "helloCache", allEntries = true)
    public String clearCache() {
        return "Кеш helloCache очищен!";
    }

}
