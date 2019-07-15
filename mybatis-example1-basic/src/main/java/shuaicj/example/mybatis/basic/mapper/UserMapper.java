package shuaicj.example.mybatis.basic.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuaicj.example.mybatis.basic.entity.User;

/**
 * The mapper for {@link User}.
 *
 * @author shuaicj 2019/06/21
 */
@Mapper
public interface UserMapper {

    void insert(User user);

    int update(User user);

    int updatePasswordByUsername(@Param("username") String username,
                                 @Param("password") String password,
                                 @Param("updatedTime") LocalDateTime updatedTime);

    int deleteAll();

    int deleteById(@Param("id") Long id);

    int deleteByUsername(@Param("username") String username);

    User findById(@Param("id") Long id);

    User findByUsername(@Param("username") String username);

    List<User> findByUsernameLike(@Param("pattern") String pattern);

    List<User> findByUsernameIn(@Param("usernames") List<String> usernames);

    List<User> findByOptionalConditions(@Param("usernamePattern") String usernamePattern,
                                        @Param("createdAfter") LocalDateTime createdAfter,
                                        @Param("updatedAfter") LocalDateTime updatedAfter);
}
