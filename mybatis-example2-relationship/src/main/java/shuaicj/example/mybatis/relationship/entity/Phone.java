package shuaicj.example.mybatis.relationship.entity;

import java.io.Serializable;

/**
 * A java bean representing a phone.
 *
 * @author shuaicj 2019/07/15
 */
@SuppressWarnings("serial")
public class Phone implements Serializable {

    private Long id;
    private String number;
    private Person person;

    public Phone() {
    }

    public Phone(String number) {
        this.number = number;
    }

    public Phone(String number, Person person) {
        this.number = number;
        this.person = person;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Phone{"
                + "id=" + id
                + ", number='" + number + '\''
                + '}';
    }
}
