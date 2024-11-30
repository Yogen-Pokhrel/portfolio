package seeder;

import com.portfolio.common.Roles;
import com.portfolio.role.RoleRepository;
import com.portfolio.role.entity.Role;
import com.portfolio.user.entity.User;
import com.portfolio.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserSeeder implements Seeder{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    List<User> users;

    @Autowired
    public UserSeeder(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        users = new ArrayList<>();
  }

    @Override
    @Transactional
    public SeederResult seed() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("testPassword");
        users.add(User.builder().firstName("Yogen").lastName("Pokhrel").email("yogen.pokhrel@portfolio.com").roles(new ArrayList<>()).password(password).build());
        users.add(User.builder().firstName("Dikshya").lastName("Prasai").email("dikshya.pokhrel@gmail.com").roles(new ArrayList<>()).password(password).build());

        return DatabaseSeederService.seed("User Seeder", users, this::processUser);
    }

    @Transactional
    public SeederResult processUser(User user) {
        SeederResult result = new SeederResult();
        Optional<User> existingRecord = userRepository.findUserByEmail(user.getEmail());
        if (existingRecord.isEmpty()) {
            Optional<Role> role = roleRepository.findBySlugWithUsers(Roles.SUPER_ADMIN.get());
            role.ifPresent(r -> {
                Role attachedRole = entityManager.merge(r);
                user.addRole(attachedRole);
            });
            System.out.println("Saving user: " + user.getEmail());
            userRepository.save(user);
            result.iAdded();
        } else {
            result.iSkipped();
        }
        return result;
    }
}
