package shuaicj.example.mybatis.relationship.entity;

import java.io.Serializable;

/**
 * A java bean representing a project.
 *
 * @author shuaicj 2019/07/03
 */
@SuppressWarnings("serial")
public class Project implements Serializable {

    private Long id;
    private String name;

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
