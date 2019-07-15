package shuaicj.example.mybatis.relationship.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuaicj.example.mybatis.relationship.entity.Project;

/**
 * The mapper for {@link Project}.
 *
 * @author shuaicj 2019/07/03
 */
@Mapper
public interface ProjectMapper {

    void insert(Project project);

    int deleteAll();

    Project findById(@Param("id") Long id);

    List<Project> findByName(@Param("name") String name);
}
