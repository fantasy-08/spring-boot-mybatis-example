package shuaicj.example.mybatis.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import shuaicj.example.mybatis.domain.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The mapper for {@link User}.
 *
 * @author shuaicj 2019/06/21
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user(username, password, created_time, updated_time) " +
            "values(#{username}, #{password}, #{createdTime}, #{updatedTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Results(id = "userResult", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "created_time", property = "createdTime"),
            @Result(column = "updated_time", property = "updatedTime")
    })
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);

    @ResultMap("userResult")
    @Select("select * from user where username = #{username}")
    User findByUsername(@Param("username") String username);

    @ResultMap("userResult")
    @Select("select * from user where username like #{pattern}")
    List<User> findByUsernameLike(@Param("pattern") String pattern);

    // TODO
    List<User> findByUsernameIn(@Param("usernames") List<String> usernames);

    @Delete("delete from user where id = #{id}")
    int deleteById(@Param("id") Long id);

    @Delete("delete from user where username = #{username}")
    int deleteByUsername(@Param("username") String username);

    @Update("update user set password = #{password}, updated_time = #{updatedTime} where id = #{id}")
    int update(User user);

    @Update("update user set password = #{password}, updated_time = #{updatedTime} where username = #{username}")
    int updatePasswordByUsername(@Param("username") String username,
                                 @Param("password") String password,
                                 @Param("updatedTime") LocalDateTime updatedTime);
}
