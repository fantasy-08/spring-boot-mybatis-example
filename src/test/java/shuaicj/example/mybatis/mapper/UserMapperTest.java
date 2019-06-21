package shuaicj.example.mybatis.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import shuaicj.example.mybatis.domain.User;

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

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername(NAME);
        assertThat(user.getId()).isGreaterThan(0L);
        assertThat(user.getPassword()).isEqualTo(PASS);
        assertThat(user.getCreatedTime()).isNotNull();
        assertThat(user.getCreatedTime()).isEqualTo(user.getUpdatedTime());
    }
}