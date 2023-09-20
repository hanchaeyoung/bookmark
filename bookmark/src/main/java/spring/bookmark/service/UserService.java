package spring.bookmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.bookmark.domain.User;
import spring.bookmark.entity.UserEntity;
import spring.bookmark.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    /*계정 추가*/
    public User add(User user) {
        UserEntity entity = UserEntity.build(user);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(entity);

        return read(entity.getUser_idx());
    }


    /* 계정 읽기 idx */
    public User read(Long userIdx) {
        Optional<UserEntity> optional = userRepository.findById(userIdx);
        if (optional.isPresent()) {
            UserEntity entity = optional.get();
            return User.build(entity);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /* 계정 읽기 id */
    public User read(String id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            UserEntity entity = optional.get();
            return User.build(entity);
        } else {
            throw new IllegalArgumentException();
        }
    }

    // 모든 사용자 목록을 가져오는 메서드
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return User.buildList(userEntities);
    }
}