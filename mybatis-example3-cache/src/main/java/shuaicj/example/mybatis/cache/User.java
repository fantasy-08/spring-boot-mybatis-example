package shuaicj.example.mybatis.cache;

import java.io.Serializable;
import java.util.Objects;

/**
 * A java bean representing a user.
 *
 * @author shuaicj 2019/08/15
 */
@SuppressWarnings("serial")
public class User implements Serializable {

    private Long id;
    private String username;
    private String address;

    public User() {
    }

    public User(Long id, String username, String address) {
        this.id = id;
        this.username = username;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, address);
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", address='" + address + '\''
                + '}';
    }
}
