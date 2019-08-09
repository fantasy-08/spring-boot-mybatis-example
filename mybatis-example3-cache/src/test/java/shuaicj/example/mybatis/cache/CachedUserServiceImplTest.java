package shuaicj.example.mybatis.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.registerCustomDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test CachedUserServiceImpl.
 *
 * @author shuaicj 2017/02/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// @IfProfileValue(name = "spring.profiles.active", values = {"default", "redis"})
public class CachedUserServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(CachedUserServiceImplTest.class);

    private static final String NAME = "shuaicj";

    @Autowired
    private Environment env;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CachedUserService userService;

    private Cache cache;

    @Before
    public void setUp() throws Exception {
        // ((RedisCacheManager) cacheManager).
        cache = cacheManager.getCache("userCache");
        Cache cache1 = cacheManager.getCache("anotherCache");
        cache.clear();
        cache1.clear();
    }

    @After
    public void tearDown() throws Exception {
        setUp();
    }

    @Test
    public void manager() throws Exception {
        String name = cacheManager.getClass().getSimpleName();
        // if (env.acceptsProfiles("default")) {
        //     assertThat(name).isEqualTo("ConcurrentMapCacheManager");
        // } else if (env.acceptsProfiles("redis")) {
        //     assertThat(name).isEqualTo("RedisCacheManager");
        // }
    }

    @Test
    public void find() throws Exception {
        assertThat(cache.get(NAME, User.class)).isNull();
        User user1 = userService.find(NAME);
        User user2 = cache.get(NAME, User.class);
        assertThat(user2.getUsername()).isEqualTo(user1.getUsername());
        assertThat(user2.getAddress()).isEqualTo(user1.getAddress());
        User user3 = userService.find(NAME);
        assertThat(user3.getUsername()).isEqualTo(user1.getUsername());
        assertThat(user3.getAddress()).isEqualTo(user1.getAddress());
    }

    @Test
    public void delete() throws Exception {
        userService.find(NAME);
        assertThat(cache.get(NAME, User.class)).isNotNull();
        userService.clearCache(NAME);
        assertThat(cache.get(NAME, User.class)).isNull();
    }

    @Test
    public void update() throws Exception {
        userService.find(NAME);
        User user1 = cache.get(NAME, User.class);
        assertThat(user1.getAddress()).isEqualTo("china");
        userService.update(NAME, "addr");
        User user2 = cache.get(NAME, User.class);
        assertThat(user2.getAddress()).isEqualTo("addr");
        User user3 = userService.find(NAME);
        assertThat(user3.getAddress()).isEqualTo("addr");
    }

    @Test
    public void blockQuery() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
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