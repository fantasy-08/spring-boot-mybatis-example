package shuaicj.example.mybatis;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shuaicj.example.mybatis.entity.User;
import shuaicj.example.mybatis.enums.Sex;
import shuaicj.example.mybatis.mapper.UserMapper;

/**
 * Description.
 *
 * @author shuaicj 2019/07/02
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional
    public void insertDuplicate() {
        userMapper.insert(createUser("shuaicj", "shuaicj", Sex.M));
        userMapper.insert(createUser("shuaicj", "shuaicj", Sex.M));
        logger.info("--------------------duplicated");
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
}
