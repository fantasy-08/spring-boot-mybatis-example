package shuaicj.example.mybatis.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import shuaicj.example.mybatis.domain.User;

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
    User findById(Long id);

    @ResultMap("userResult")
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    @Delete("delete from user where username = #{username}")
    int deleteByUsername(String username);
}
