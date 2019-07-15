package shuaicj.example.mybatis.relationship.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuaicj.example.mybatis.relationship.entity.Person;

/**
 * The mapper for {@link Person}.
 *
 * @author shuaicj 2019/07/03
 */
@Mapper
public interface PersonMapper {

    void insert(Person person);

    int deleteAll();

    Person findById(@Param("id") Long id);

    Person findByIdentityId(@Param("identityId") Long identityId);

    Person findDetailByName(@Param("name") String name);
}
