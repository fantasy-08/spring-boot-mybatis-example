package shuaicj.example.mybatis.relationship.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuaicj.example.mybatis.relationship.entity.Phone;

import java.util.List;

/**
 * The mapper for {@link Phone}.
 *
 * @author shuaicj 2019/07/15
 */
@Mapper
public interface PhoneMapper {

    void insert(Phone phone);

    int deleteAll();

    Phone findById(@Param("id") Long id);

    List<Phone> findByPersonId(@Param("personId") Long personId);

    Phone findDetailByNumber(@Param("number") String number);
}
