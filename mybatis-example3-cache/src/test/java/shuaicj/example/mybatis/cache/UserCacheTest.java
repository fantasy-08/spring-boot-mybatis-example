package shuaicj.example.mybatis.cache;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import shuaicj.example.mybatis.cache.entity.User;
import shuaicj.example.mybatis.cache.enums.Sex;
import shuaicj.example.mybatis.cache.mapper.UserMapper;

/**
 * Test cache of {@link UserMapper}.
 *
 * @author shuaicj 2019/08/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCacheTest {

    private static final String NAME = "shuaicj";
    private static final String PASS = "pass123";
    private static final String PASS2 = "pass456";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CacheManager cacheManager;

    private Cache cache;

    @Before
    public void setUp() {
        userMapper.deleteAll();
        cache = cacheManager.getCache("userCache");
        assertThat(cache).isNotNull();
        cache.clear();
    }

    @After
    public void tearDown() {
        setUp();
    }

    @Test
    public void findByUsername() {
        assertThat(cache.get(NAME, User.class)).isNull();
        userMapper.insert(createUser(NAME, PASS));
        User user1 = userMapper.findByUsername(NAME);
        User user2 = cache.get(NAME, User.class);
        assertThat(user2).isEqualTo(user1);
        User user3 = userMapper.findByUsername(NAME);
        assertThat(user3).isEqualTo(user1);
    }

    @Test
    public void deleteByUsername() {
        userMapper.insert(createUser(NAME, PASS));
        userMapper.findByUsername(NAME);
        assertThat(cache.get(NAME, User.class)).isNotNull();
        userMapper.deleteByUsername(NAME);
        assertThat(cache.get(NAME, User.class)).isNull();
    }

    @Test
    public void updatePasswordByUsername() {
        userMapper.insert(createUser(NAME, PASS));
        userMapper.findByUsername(NAME);
        assertThat(cache.get(NAME, User.class)).isNotNull();
        userMapper.updatePasswordByUsername(NAME, PASS2, Instant.now());
        assertThat(cache.get(NAME, User.class)).isNull();
    }

    private User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setSex(Sex.M);
        user.setCreatedTime(Instant.now());
        user.setUpdatedTime(user.getCreatedTime());
        return user;
    }
}