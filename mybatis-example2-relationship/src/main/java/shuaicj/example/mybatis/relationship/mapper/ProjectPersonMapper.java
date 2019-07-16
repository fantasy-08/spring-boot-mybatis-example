package shuaicj.example.mybatis.relationship.mapper;

import org.apache.ibatis.annotations.Mapper;
import shuaicj.example.mybatis.relationship.entity.ProjectPerson;

/**
 * The mapper for {@link ProjectPerson}.
 *
 * @author shuaicj 2019/07/03
 */
@Mapper
public interface ProjectPersonMapper {

    void insert(ProjectPerson projectPerson);

    int deleteAll();
}
