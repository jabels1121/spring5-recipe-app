package guru.springframework.security;

import guru.springframework.security.dao.User;
import guru.springframework.security.dao.UserRepository;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        final User byLogin = userRepository.findByLogin(login);
        if (byLogin == null) {
            throw new UsernameNotFoundException("User with login - " + login + " not found!");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(byLogin.getLogin())
                .password(byLogin.getPassword())
                .authorities(byLogin.getRoles()).build();
    }

}
