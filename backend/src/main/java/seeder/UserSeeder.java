package seeder;

import com.portfolio.role.entity.Role;
import com.portfolio.user.entity.User;
import com.portfolio.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserSeeder implements Seeder{

    private final UserRepository userRepository;
    List<User> users;

    @Autowired
    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
        users = new ArrayList<>();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("testPassword");
        users.add(User.builder().firstName("Yogen").lastName("Pokhrel").email("yogen.pokhrel@gmail.com").password(password).build());
        users.add(User.builder().firstName("Dikshya").lastName("Prasai").email("dikshya.pokhrel@gmail.com").password(password).build());
    }

    @Override
    public SeederResult seed() {
        return DatabaseSeederService.seed("User Seeder", users, user -> {
            SeederResult result = new SeederResult();
            Optional<User> existingRecord = userRepository.findUserByEmail(user.getEmail());
            if (existingRecord.isEmpty()) {
                userRepository.save(user);
                result.iAdded();
            } else {
                result.iSkipped();
            }
            return result;
        });
    }
}
