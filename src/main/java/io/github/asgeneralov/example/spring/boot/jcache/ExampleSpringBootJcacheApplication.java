package io.github.asgeneralov.example.spring.boot.jcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ExampleSpringBootJcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringBootJcacheApplication.class, args);
    }

}
