package spring.bookmark.domain;

import lombok.Getter;
import lombok.Setter;
import spring.bookmark.entity.UserEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long user_idx;

    @Column(length = 100, nullable = false)
    private String id;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private int role;

    public static User build(String id, String password, String username, String address, String email) {
        User user = new User();

        user.setId(id);
        user.setPassword(password);
        user.setUsername(username);
        user.setAddress(address);
        user.setEmail(email);

        return user;
    }

    public static User build(UserEntity userEntity) {
        User user = new User();

        user.setUser_idx(userEntity.getUser_idx());
        user.setId(userEntity.getId());
        user.setPassword(userEntity.getPassword());
        user.setUsername(userEntity.getUsername());
        user.setAddress(userEntity.getAddress());
        user.setEmail(userEntity.getEmail());
        user.setRole(userEntity.getRole());

        return user;
    }

    public static List<User> buildList(List<UserEntity> userEntities) {
        List<User> userList = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            userList.add(build(userEntity));
        }
        return userList;
    }
}
