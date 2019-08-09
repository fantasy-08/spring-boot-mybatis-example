package shuaicj.example.mybatis.basic.entity;

import java.io.Serializable;
import java.time.Instant;

import shuaicj.example.mybatis.basic.enums.Sex;

/**
 * A java bean representing a user.
 *
 * @author shuaicj 2019/06/21
 */
@SuppressWarnings("serial")
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Sex sex;
    private Instant createdTime;
    private Instant updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", password='" + password + '\''
                + ", sex=" + sex
                + ", createdTime=" + createdTime
                + ", updatedTime=" + updatedTime
                + '}';
    }
}
