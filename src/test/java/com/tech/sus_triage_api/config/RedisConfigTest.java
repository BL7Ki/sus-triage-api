package com.tech.sus_triage_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import static org.junit.jupiter.api.Assertions.*;

class RedisConfigTest {
    @Test
    void cacheConfigurationReturnsValidConfig() {
        RedisConfig config = new RedisConfig();
        RedisCacheConfiguration cacheConfig = config.cacheConfiguration();
        assertNotNull(cacheConfig);
        assertFalse(cacheConfig.getAllowCacheNullValues());
        assertNotNull(cacheConfig.getValueSerializationPair());
    }
}