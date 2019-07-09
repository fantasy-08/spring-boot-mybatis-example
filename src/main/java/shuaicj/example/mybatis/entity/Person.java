package shuaicj.example.mybatis.entity;

import java.io.Serializable;

/**
 * A java bean representing a person.
 *
 * @author shuaicj 2019/07/03
 */
@SuppressWarnings("serial")
public class Person implements Serializable {

    private Long id;
    private String name;
    private Long identityId;

    public Person() {
    }

    public Person(String name, Long identityId) {
        this.name = name;
        this.identityId = identityId;
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

    public Long getIdentityId() {
        return identityId;
    }

    public void setIdentityId(Long identityId) {
        this.identityId = identityId;
    }

    @Override
    public String toString() {
        return "Person{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", identityId=" + identityId
                + '}';
    }
}
