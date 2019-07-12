package shuaicj.example.mybatis.basic.entity;

import shuaicj.example.mybatis.basic.enums.Sex;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
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
