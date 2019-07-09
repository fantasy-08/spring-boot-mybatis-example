package shuaicj.example.mybatis.entity;

import java.io.Serializable;

/**
 * A java bean representing an identity.
 *
 * @author shuaicj 2019/07/03
 */
@SuppressWarnings("serial")
public class IdentityDO implements Serializable {

    private Long id;
    private String number;
    private PersonDO person;

    public IdentityDO() {
    }

    public IdentityDO(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PersonDO getPerson() {
        return person;
    }

    public void setPerson(PersonDO person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "IdentityDetail{"
                + "id=" + id
                + ", number='" + number + '\''
                + ", person=" + person
                + '}';
    }
}
