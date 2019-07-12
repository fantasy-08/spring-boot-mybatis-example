package shuaicj.example.mybatis.relationship.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuaicj.example.mybatis.relationship.entity.Identity;
import shuaicj.example.mybatis.relationship.entity.IdentityDO;
import shuaicj.example.mybatis.relationship.entity.IdentityDetail;

/**
 * The mapper for {@link Identity}.
 *
 * @author shuaicj 2019/07/03
 */
@Mapper
public interface IdentityMapper {

    void insert(Identity identity);

    int deleteAll();

    Identity findById(@Param("id") Long id);

    IdentityDetail findDetailById(@Param("id") Long id);

    IdentityDO findDoById(@Param("id") Long id);
}
