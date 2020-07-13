package guru.springframework.security;

import guru.springframework.repositories.reactive.UserReactiveRepository;
import guru.springframework.security.entities.User;
import guru.springframework.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReactiveRepository userReactiveRepository;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  UserReactiveRepository userReactiveRepository) {
        this.userRepository = userRepository;
        this.userReactiveRepository = userReactiveRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        var user = userReactiveRepository.findByLogin(login).block();
        if (user == null) {
            throw new UsernameNotFoundException("User with login - " + login + " not found!");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(user.getRoles()).build();
        /*final User byLogin = userRepository.findByLogin(login);
        if (byLogin == null) {
            throw new UsernameNotFoundException("User with login - " + login + " not found!");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(byLogin.getLogin())
                .password(byLogin.getPassword())
                .authorities(byLogin.getRoles()).build();*/
    }

}
