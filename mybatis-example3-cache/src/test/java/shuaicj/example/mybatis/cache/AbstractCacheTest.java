package shuaicj.example.mybatis.cache;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * Provides common test cases.
 *
 * @author shuaicj 2019/08/15
 */
public class AbstractCacheTest {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCacheTest.class);

    private static final String NAME = "shuaicj";

    private CacheManager cacheManager;
    private CachedUserService userService;
    private Cache cache;

    protected void setUp(CacheManager cacheManager, CachedUserService userService) {
        this.cacheManager = cacheManager;
        this.userService = userService;
        this.cache = cacheManager.getCache("userCache");
        assertThat(this.cache).isNotNull();
        this.cache.clear();
    }

    protected void tearDown() {
        this.cache.clear();
    }

    protected void assertManagerIsInstanceOf(Class<?> clazz) {
        assertThat(cacheManager).isInstanceOf(clazz);
    }

    protected void find() throws Exception {
        assertThat(cache.get(NAME, User.class)).isNull();
        User user1 = userService.find(NAME);
        User user2 = cache.get(NAME, User.class);
        assertThat(user2.getUsername()).isEqualTo(user1.getUsername());
        assertThat(user2.getAddress()).isEqualTo(user1.getAddress());
        User user3 = userService.find(NAME);
        assertThat(user3.getUsername()).isEqualTo(user1.getUsername());
        assertThat(user3.getAddress()).isEqualTo(user1.getAddress());
    }

    protected void delete() throws Exception {
        userService.find(NAME);
        assertThat(cache.get(NAME, User.class)).isNotNull();
        userService.clearCache(NAME);
        assertThat(cache.get(NAME, User.class)).isNull();
    }

    protected void update() throws Exception {
        userService.find(NAME);
        User user1 = cache.get(NAME, User.class);
        assertThat(user1.getAddress()).isEqualTo("china");
        userService.update(NAME, "addr");
        User user2 = cache.get(NAME, User.class);
        assertThat(user2.getAddress()).isEqualTo("addr");
        User user3 = userService.find(NAME);
        assertThat(user3.getAddress()).isEqualTo("addr");
    }

    protected void blockQuery() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10,
                new CustomizableThreadFactory("block-query-thread-"));
        List<Future<User>> futures = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Future<User> future = executorService.submit(() -> {
                long start = System.currentTimeMillis();
                User u = userService.find(NAME);
                long end = System.currentTimeMillis();
                logger.info("blockQuery-{} time cost: {}", index, (end - start));
                return u;
            });
            futures.add(future);
        }
        executorService.shutdown();
        List<User> users = new ArrayList<>(10);
        for (Future<User> future : futures) {
            users.add(future.get());
        }
        assertThat(users).hasSize(10);
    }
}