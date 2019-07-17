package shuaicj.example.mybatis.relationship.entity;

import java.io.Serializable;
import java.util.List;

/**
 * A java bean representing a person detail.
 *
 * @author shuaicj 2019/07/03
 */
@SuppressWarnings("serial")
public class Person implements Serializable {

    private Long id;
    private String name;
    private Identity identity;
    private List<Phone> phones;
    private List<Project> projects;

    public Person() {
    }

    public Person(String name) {
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

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Person{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", identity=" + identity
                + ", phones=" + phones
                + ", projects=" + projects
                + '}';
    }
}
