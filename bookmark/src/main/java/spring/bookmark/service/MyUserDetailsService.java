package spring.bookmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.bookmark.domain.MyUserDetails;
import spring.bookmark.domain.User;
import spring.bookmark.repository.UserRepository;

@Service(value = "userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    private UserRepository userRepository;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        try {
            User user = userService.read(username);
            return new MyUserDetails(user);
        } catch (IllegalArgumentException e) {
            throw new UsernameNotFoundException(username);
        }
    }
}
