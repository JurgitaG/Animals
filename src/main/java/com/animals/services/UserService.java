package com.animals.services;
import com.animals.models.Users;
import com.animals.repositories.UsersRepository;
import com.animals.requests.UsersRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static com.animals.config.SecurityConstants.EXPIRATION_TIME;
import static com.animals.config.SecurityConstants.KEY;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

@Component
public class UserService implements UserDetailsService{
    @Autowired
    private UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Users not found");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
    private final UsersRepository userRepository;

    private final PasswordEncoder encoder;

    public UserService(
            final UsersRepository userRepository,
            final PasswordEncoder encoder
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Users login(String username, String password) {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Vartotojo vardas arba slaptažodis yra neteisingas");
        }
        if (!encoder.matches(password, user.getPassword())) {
            throw new UsernameNotFoundException("Vartotojo vardas arba slaptažodis yra neteisingas");
        }
        String token = getAuthorizationToken(username);
        user.setToken(token);
        return userRepository.save(user);
    }

    private String getAuthorizationToken(String username) {
        return generateToken(username);
    }

    private String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

    public void  logout(Users user) {
        user.setToken("");
        userRepository.save(user);
    }

    public Users registerUser(UsersRequest request) {
        Long users = userRepository.countByUsername(request.getUsername());
        if (users == 0) {
            validateRequest(request);
            Users user = new Users(
                    request.getUsername(),
                    encoder.encode(request.getPassword())
            );
            return userRepository.save(user);
        }
        throw new UsernameNotFoundException("Vartotojo vardas arba slaptažodis yra neteisingas");
    }

    private void validateRequest(UsersRequest request) {
        if (request.getUsername() == null || request.getUsername().length() == 0
                || request.getPassword() == null || request.getPassword().length() == 0
        ) {
            throw new UsernameNotFoundException("Užpildyti laukus");
        }
    }
}