package shuaicj.example.mybatis.relationship.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuaicj.example.mybatis.relationship.entity.Person;

import java.util.List;

/**
 * The mapper for {@link Person}.
 *
 * @author shuaicj 2019/07/03
 */
@Mapper
public interface PersonMapper {

    void insert(Person person);

    int deleteAll();

    List<Person> findByName(@Param("name") String name);
}
