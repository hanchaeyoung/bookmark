package spring.bookmark.entity;

import lombok.Getter;
import lombok.Setter;
import spring.bookmark.domain.User;

import javax.persistence.*;

@Entity
@Table(name = "user_entity")
@Getter
@Setter
public class UserEntity {

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


    public static UserEntity build(User user) {
        UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setPassword(user.getPassword());
        entity.setUsername(user.getUsername());
        entity.setAddress(user.getAddress());
        entity.setEmail(user.getEmail());

        return entity;
    }
}
