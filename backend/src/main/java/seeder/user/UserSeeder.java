package seeder.user;

import com.portfolio.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import seeder.DatabaseSeederService;
import seeder.Seeder;
import seeder.SeederResult;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSeeder implements Seeder {
    private final UserSeederService userSeederService;
    private List<User> users;

    @Autowired
    public UserSeeder(UserSeederService userSeederService) {
        this.userSeederService = userSeederService;
        users = new ArrayList<>();
    }

    @Override
    public SeederResult seed() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("testPassword");

        users.add(User.builder()
                .firstName("Yogen")
                .lastName("Pokhrel")
                .email("yogen.pokhrel@gmail.com")
                .roles(new ArrayList<>())
                .password(password)
                .build());

        users.add(User.builder()
                .firstName("Dikshya")
                .lastName("Prasai")
                .email("dikshya.pokhrel@gmail.com")
                .roles(new ArrayList<>())
                .password(password)
                .build());

        return DatabaseSeederService.seed("User Seeder", users, userSeederService::processUser);  // Use processUser from UserService
    }
}
