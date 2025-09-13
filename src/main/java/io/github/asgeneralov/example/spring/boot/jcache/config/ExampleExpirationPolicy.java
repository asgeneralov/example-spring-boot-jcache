package io.github.asgeneralov.example.spring.boot.jcache.config;

import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;

import org.jspecify.annotations.NonNull;


public class ExampleExpirationPolicy implements ExpiryPolicy {
    
    private final Duration createExpiry;
    private final Duration accessExpiry;
    private final Duration updateExpiry;
    
    public ExampleExpirationPolicy(@NonNull Duration createExpiry) {
        this.createExpiry = createExpiry;
        this.accessExpiry = null;
        this.updateExpiry = null;
    }

    public ExampleExpirationPolicy(@NonNull Duration createExpiry, 
        @NonNull Duration accessExpiry, 
        @NonNull Duration updateExpiry) {
        this.createExpiry = createExpiry;
        this.accessExpiry = accessExpiry;
        this.updateExpiry = updateExpiry;
    }

    @Override
    public Duration getExpiryForCreation() {
        return createExpiry;
    }

    @Override
    public Duration getExpiryForAccess() {
       return accessExpiry;
    }

    @Override
    public Duration getExpiryForUpdate() {
        return updateExpiry;
    }

}
