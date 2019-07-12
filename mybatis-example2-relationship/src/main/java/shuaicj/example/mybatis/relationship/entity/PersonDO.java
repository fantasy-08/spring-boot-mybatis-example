package shuaicj.example.mybatis.relationship.entity;

import java.io.Serializable;

/**
 * A java bean representing a person detail.
 *
 * @author shuaicj 2019/07/03
 */
@SuppressWarnings("serial")
public class PersonDO implements Serializable {

    private Long id;
    private String name;
    private IdentityDO identity;

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

    public IdentityDO getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityDO identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "PersonDetail{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", identity=" + identity
                + '}';
    }
}
