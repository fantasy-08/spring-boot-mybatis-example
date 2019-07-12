package shuaicj.example.mybatis.relationship.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuaicj.example.mybatis.relationship.entity.Person;
import shuaicj.example.mybatis.relationship.entity.PersonDO;
import shuaicj.example.mybatis.relationship.entity.PersonDetail;

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

    PersonDetail findDetailById(@Param("id") Long id);

    PersonDetail findDetailById2(@Param("id") Long id);

    PersonDO findDoById(@Param("id") Long id);

    PersonDO findDoByIdentityId(@Param("identityId") Long identityId);

    // List<Person> findByName(@Param("name") String name);
}
