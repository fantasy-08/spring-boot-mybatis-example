package shuaicj.example.mybatis.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;
import shuaicj.example.mybatis.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private static final String NAME2 = "shuaicj2";
    private static final String PASS2 = "pass456";
    private static final String NAME_PATTERN = "shuai%";
    private static final String NAME_PATTERN_NOT_EXISTS = "abcd%";
    private static final Long ID_NOT_EXISTS = 1000000L;

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

    @Test(expected = DuplicateKeyException.class)
    public void insertDuplicate() {
        userMapper.insert(createUser(NAME, PASS));
        userMapper.insert(createUser(NAME, PASS));
    }

    @Test
    public void insertOk() {
        userMapper.insert(createUser(NAME, PASS));
        assertThat(userMapper.findByUsername(NAME)).isNotNull();
    }

    @Test
    public void insertReturnsIdAutoIncrement() {
        User u1 = createUser(NAME, PASS);
        userMapper.insert(u1);
        User u2 = createUser(NAME2, PASS2);
        userMapper.insert(u2);
        assertThat(u2.getId()).isEqualTo(u1.getId() + 1);
    }

    @Test
    public void findByIdNotExists() {
        assertThat(userMapper.findById(ID_NOT_EXISTS)).isNull();
    }

    @Test
    public void findByIdOk() {
        User u = createUser(NAME, PASS);
        userMapper.insert(u);
        User user = userMapper.findById(u.getId());
        assertThat(user.getId()).isGreaterThan(0L);
        assertThat(user.getPassword()).isEqualTo(PASS);
        assertThat(user.getCreatedTime()).isNotNull();
        assertThat(user.getCreatedTime()).isEqualTo(user.getUpdatedTime());
    }

    @Test
    public void findByUsernameNotExists() {
        assertThat(userMapper.findByUsername(NAME)).isNull();
    }

    @Test
    public void findByUsernameOk() {
        userMapper.insert(createUser(NAME, PASS));
        User user = userMapper.findByUsername(NAME);
        assertThat(user.getId()).isGreaterThan(0L);
        assertThat(user.getPassword()).isEqualTo(PASS);
        assertThat(user.getCreatedTime()).isNotNull();
        assertThat(user.getCreatedTime()).isEqualTo(user.getUpdatedTime());
    }

    @Test
    public void findByUsernamePatternNotExists() {
        List<User> users = userMapper.findByUsernameLike(NAME_PATTERN_NOT_EXISTS);
        assertThat(users).isEmpty();
    }

    @Test
    public void findByUsernamePatternOk() {
        userMapper.insert(createUser(NAME, PASS));
        userMapper.insert(createUser(NAME2, PASS2));
        List<User> users = userMapper.findByUsernameLike(NAME_PATTERN);
        List<String> names = users.stream().map(User::getUsername).collect(Collectors.toList());
        assertThat(names).containsExactly(NAME, NAME2);
    }

    @Test
    public void deleteByIdNotExists() {
        int num = userMapper.deleteById(ID_NOT_EXISTS);
        assertThat(num).isEqualTo(0);
    }

    @Test
    public void deleteByIdOk() {
        User user = createUser(NAME, PASS);
        userMapper.insert(user);
        int num = userMapper.deleteById(user.getId());
        assertThat(num).isEqualTo(1);
    }

    @Test
    public void deleteByUsernameNotExists() {
        int num = userMapper.deleteByUsername(NAME);
        assertThat(num).isEqualTo(0);
    }

    @Test
    public void deleteByUsernameOk() {
        userMapper.insert(createUser(NAME, PASS));
        int num = userMapper.deleteByUsername(NAME);
        assertThat(num).isEqualTo(1);
    }

    @Test
    public void updateNotExists() {
        int num = userMapper.update(createUser(NAME, PASS));
        assertThat(num).isEqualTo(0);
    }

    @Test
    public void updateOk() {
        User u = createUser(NAME, PASS);
        userMapper.insert(u);
        LocalDateTime now = LocalDateTime.now();
        u.setPassword(PASS2);
        u.setUpdatedTime(now);
        int num = userMapper.update(u);
        assertThat(num).isEqualTo(1);
        User user = userMapper.findByUsername(NAME);
        assertThat(user.getPassword()).isEqualTo(PASS2);
        assertThat(user.getUpdatedTime()).isEqualTo(now);
    }

    @Test
    public void updatePasswordByUsernameNotExists() {
        int num = userMapper.updatePasswordByUsername(NAME, PASS2, LocalDateTime.now());
        assertThat(num).isEqualTo(0);
    }

    @Test
    public void updatePasswordByUsernameOk() {
        userMapper.insert(createUser(NAME, PASS));
        LocalDateTime now = LocalDateTime.now();
        int num = userMapper.updatePasswordByUsername(NAME, PASS2, now);
        assertThat(num).isEqualTo(1);
        User user = userMapper.findByUsername(NAME);
        assertThat(user.getPassword()).isEqualTo(PASS2);
        assertThat(user.getUpdatedTime()).isEqualTo(now);
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