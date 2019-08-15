package shuaicj.example.mybatis.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * The interface for cached user.
 *
 * @author shuaicj 2019/08/15
 */
@CacheConfig(cacheNames = "userCache")
public interface CachedUserService {

    @Cacheable(sync = true)
    User find(String username);

    @CacheEvict
    void clearCache(String username);

    @CachePut(key = "#username")
    User update(String username, String address);
}
