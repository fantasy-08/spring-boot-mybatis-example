package shuaicj.example.mybatis.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import shuaicj.example.mybatis.entity.User;
import shuaicj.example.mybatis.enums.Sex;

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
        userMapper.deleteAll();
    }

    @After
    public void tearDown() {
        userMapper.deleteAll();
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
    public void updateNotExists() {
        User user = createUser(NAME, PASS);
        user.setId(ID_NOT_EXISTS);
        int num = userMapper.update(user);
        assertThat(num).isEqualTo(0);
    }

    @Test
    public void updateOk() {
        User u = createUser(NAME, PASS);
        userMapper.insert(u);
        LocalDateTime now = LocalDateTime.now();
        u.setPassword(PASS2);
        u.setSex(Sex.F);
        u.setUpdatedTime(now);
        int num = userMapper.update(u);
        assertThat(num).isEqualTo(1);
        User user = userMapper.findByUsername(NAME);
        assertThat(user.getPassword()).isEqualTo(PASS2);
        assertThat(user.getSex()).isEqualTo(Sex.F);
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

    @Test
    public void deleteAllWhenTableEmpty() {
        int num = userMapper.deleteAll();
        assertThat(num).isEqualTo(0);
    }

    @Test
    public void deleteAllWhenTableNotEmpty() {
        userMapper.insert(createUser(NAME, PASS));
        userMapper.insert(createUser(NAME2, PASS2));
        int num = userMapper.deleteAll();
        assertThat(num).isEqualTo(2);
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
    public void findByUsernameLikeNotExists() {
        List<User> users = userMapper.findByUsernameLike(NAME_PATTERN_NOT_EXISTS);
        assertThat(users).isEmpty();
    }

    @Test
    public void findByUsernameLikeOk() {
        userMapper.insert(createUser(NAME, PASS));
        userMapper.insert(createUser(NAME2, PASS2));
        assertThat(getNames(userMapper.findByUsernameLike(NAME_PATTERN))).containsExactly(NAME, NAME2);
    }

    @Test(expected = BadSqlGrammarException.class)
    public void findByUsernameInEmptyList() {
        userMapper.findByUsernameIn(Collections.emptyList());
    }

    @Test
    public void findByUsernameInNotExists() {
        assertThat(userMapper.findByUsernameIn(Arrays.asList(NAME, NAME2))).isEmpty();
    }

    @Test
    public void findByUsernameInOk() {
        userMapper.insert(createUser(NAME, PASS));
        userMapper.insert(createUser(NAME2, PASS2));
        assertThat(getNames(userMapper.findByUsernameIn(Arrays.asList(NAME, NAME2)))).containsExactly(NAME, NAME2);
    }

    @Test
    public void findByOptionalConditions() throws InterruptedException {
        LocalDateTime t0 = LocalDateTime.now();
        TimeUnit.MILLISECONDS.sleep(10);
        User u1 = createUser(NAME, PASS);
        userMapper.insert(u1);
        LocalDateTime t1 = LocalDateTime.now();
        TimeUnit.MILLISECONDS.sleep(10);
        User u2 = createUser(NAME2, PASS2);
        userMapper.insert(u2);
        LocalDateTime t2 = LocalDateTime.now();
        TimeUnit.MILLISECONDS.sleep(10);
        u1.setPassword(PASS2);
        u1.setUpdatedTime(LocalDateTime.now());
        userMapper.update(u1);
        assertThat(getNames(userMapper.findByOptionalConditions(null, null, null))).containsExactly(NAME, NAME2);
        assertThat(getNames(userMapper.findByOptionalConditions(NAME2 + "%", null, null))).containsExactly(NAME2);
        assertThat(getNames(userMapper.findByOptionalConditions(null, t1, null))).containsExactly(NAME2);
        assertThat(getNames(userMapper.findByOptionalConditions(null, null, t2))).containsExactly(NAME);
        assertThat(getNames(userMapper.findByOptionalConditions(NAME_PATTERN, t1, null))).containsExactly(NAME2);
        assertThat(getNames(userMapper.findByOptionalConditions(NAME_PATTERN, null, t2))).containsExactly(NAME);
        assertThat(getNames(userMapper.findByOptionalConditions(null, t0, t2))).containsExactly(NAME);
        assertThat(getNames(userMapper.findByOptionalConditions(NAME_PATTERN, t0, t2))).containsExactly(NAME);
    }

    @Test
    @Transactional
    public void mapperReturnsSameReferenceBecauseLocalSessionCacheInTransaction() {
        userMapper.insert(createUser(NAME, PASS));
        User user1 = userMapper.findByUsername(NAME);
        User user2 = userMapper.findByUsername(NAME);
        assertThat(user1).isSameAs(user2);
    }

    @Test
    public void mapperReturnsDifferentReferencesBecauseNotInOneTransaction() {
        userMapper.insert(createUser(NAME, PASS));
        User user1 = userMapper.findByUsername(NAME);
        User user2 = userMapper.findByUsername(NAME);
        assertThat(user1).isNotSameAs(user2);
        assertThat(user1.getId()).isEqualTo(user2.getId());
    }

    private User createUser(String username, String password) {
        return createUser(username, password, Sex.M);
    }

    private User createUser(String username, String password, Sex sex) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setSex(sex);
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(user.getCreatedTime());
        return user;
    }

    private List<String> getNames(List<User> users) {
        return users.stream().map(User::getUsername).collect(Collectors.toList());
    }
}