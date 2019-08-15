package shuaicj.example.mybatis.cache;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.cache.Caching;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.spi.CachingProvider;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import com.github.benmanes.caffeine.jcache.spi.CaffeineCachingProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
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

    /**
     * The simple ConcurrentMap cache. It does not support expiration.
     */
    @Bean
    @Profile("concurrent-map")
    public CacheManager concurrentMapCacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setStoreByValue(true); // return a copy of cached object
        return cacheManager;
    }

    /**
     * The Caffeine original.
     * Note it does not support 'setStoreByValue'. To do so, see {@link #jcacheCaffeineCacheManager()}.
     */
    @Bean
    @Profile("caffeine")
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new CaffeineCache("userCache", Caffeine.newBuilder()
                                                       .expireAfterAccess(Duration.ofSeconds(60L))
                                                       .build()),
                new CaffeineCache("anotherCache", Caffeine.newBuilder()
                                                          .expireAfterAccess(Duration.ofSeconds(120L))
                                                          .build())
        ));
        return cacheManager;
    }

    /**
     * The Caffeine as JCache. Support java config, and also Typesafe config file.
     * See https://github.com/ben-manes/caffeine/blob/master/jcache/src/test/resources/application.conf.
     */
    @Bean
    @Profile("jcache-caffeine")
    public CacheManager jcacheCaffeineCacheManager() {
        CachingProvider provider = Caching.getCachingProvider(CaffeineCachingProvider.class.getName());
        javax.cache.CacheManager cacheManager = provider.getCacheManager();

        CaffeineConfiguration<Object, Object> config1 = new CaffeineConfiguration<>();
        config1.setExpiryPolicyFactory(() -> new AccessedExpiryPolicy(new javax.cache.expiry.Duration(TimeUnit.SECONDS, 60L)));
        config1.setStoreByValue(true); // return a copy of cached object

        CaffeineConfiguration<Object, Object> config2 = new CaffeineConfiguration<>();
        config2.setExpiryPolicyFactory(() -> new AccessedExpiryPolicy(new javax.cache.expiry.Duration(TimeUnit.SECONDS, 120L)));
        config2.setStoreByValue(true); // return a copy of cached object

        cacheManager.createCache("userCache", config1);
        cacheManager.createCache("anotherCache", config2);

        return new JCacheCacheManager(cacheManager);
    }

    /**
     * The redis cache.
     * It always returns a copy of cached object, same as the effect of 'setStoreByValue' in JCache.
     */
    @Bean
    @Profile("redis")
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        // choose locking or non-locking
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        // default ttl
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                                                                       .entryTtl(Duration.ofSeconds(600L));
        // set different ttl for different cache
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("userCache", RedisCacheConfiguration.defaultCacheConfig()
                                                          .entryTtl(Duration.ofSeconds(60L)));
        configMap.put("anotherCache", RedisCacheConfiguration.defaultCacheConfig()
                                                             .entryTtl(Duration.ofSeconds(120L)));

        return RedisCacheManager.builder(redisCacheWriter)
                                .cacheDefaults(defaultConfig)
                                .withInitialCacheConfigurations(configMap)
                                .build();
    }
}
