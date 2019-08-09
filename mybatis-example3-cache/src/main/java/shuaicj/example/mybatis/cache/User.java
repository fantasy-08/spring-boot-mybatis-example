package shuaicj.example.mybatis.cache;

import java.io.Serializable;

/**
 * A java bean representing a user.
 *
 * @author shuaicj 2017/04/18
 */
@SuppressWarnings("serial")
public class User implements Serializable {

    private String username;
    private String address;

    public User() {
    }

    public User(String username, String address) {
        this.username = username;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
