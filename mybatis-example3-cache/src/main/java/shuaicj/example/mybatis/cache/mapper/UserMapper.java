package shuaicj.example.mybatis.cache.mapper;

import java.time.Instant;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import shuaicj.example.mybatis.cache.entity.User;

/**
 * The mapper for {@link User}.
 *
 * @author shuaicj 2019/08/19
 */
@Mapper
@CacheConfig(cacheNames = "userCache")
public interface UserMapper {

    void insert(User user);

    @CacheEvict(key = "#username")
    int updatePasswordByUsername(@Param("username") String username,
                                 @Param("password") String password,
                                 @Param("updatedTime") Instant updatedTime);

    int deleteAll();

    @CacheEvict
    int deleteByUsername(@Param("username") String username);

    @Cacheable(sync = true)
    User findByUsername(@Param("username") String username);
}
