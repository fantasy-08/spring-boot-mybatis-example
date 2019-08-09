package shuaicj.example.mybatis.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Application configuration.
 *
 * @author shuaicj 2019/07/19
 */
@Configuration
public class ApplicationConfig {

    @Bean
    @Profile("redis")
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // choose locking or non-locking
        // RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        // default ttl
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(600L));
        // set different ttl for different cache
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("userCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(60L)));
        configMap.put("anotherCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(120L)));

        return RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(defaultConfig)
                                .withInitialCacheConfigurations(configMap)
                                .build();
    }

    // @Bean
    // public CacheManager cacheManager() {
    //     ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
    //     cacheManager.setStoreByValue(true);
    //     return cacheManager;
    // }
}
