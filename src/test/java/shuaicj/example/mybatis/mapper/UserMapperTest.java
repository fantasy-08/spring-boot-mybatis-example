package shuaicj.example.mybatis.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import shuaicj.example.mybatis.domain.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link UserMapper}.
 *
 * @author shuaicj 2019/06/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    private static final String NAME = "shuaicj";
    private static final String PASS = "pass123";
    private static final String NAME2 = "newuser";
    private static final String PASS2 = "newpass";

    @Autowired
    private UserMapper userMapper;

    @Before
    public void setUp() {
        userMapper.deleteByUsername(NAME);
        userMapper.deleteByUsername(NAME2);
    }

    @After
    public void tearDown() {
        setUp();
    }

    @Test
    public void insert() {
        assertThat(userMapper.findByUsername(NAME)).isNull();
        User user = createUser(NAME, PASS);
        userMapper.insert(user);
        assertThat(user.getId()).isGreaterThan(0L);
        assertThat(userMapper.findByUsername(NAME)).isNotNull();
    }

    @Test
    public void findByUsername() {
        userMapper.insert(createUser(NAME, PASS));
        User user = userMapper.findByUsername(NAME);
        assertThat(user.getId()).isGreaterThan(0L);
        assertThat(user.getPassword()).isEqualTo(PASS);
        assertThat(user.getCreatedTime()).isNotNull();
        assertThat(user.getCreatedTime()).isEqualTo(user.getUpdatedTime());
    }

    private User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(user.getCreatedTime());
        return user;
    }
}