package shuaicj.example.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
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

    @Results({
            @Result(column = "created_time", property = "createdTime"),
            @Result(column = "updated_time", property = "updatedTime")
    })
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);
}
