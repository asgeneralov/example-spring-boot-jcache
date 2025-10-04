package io.github.asgeneralov.example.spring.boot.jcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ExampleSpringBootJcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringBootJcacheApplication.class, args);
    }

}
