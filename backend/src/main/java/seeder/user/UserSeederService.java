package seeder.user;

import com.portfolio.common.Roles;
import com.portfolio.role.RoleRepository;
import com.portfolio.role.entity.Role;
import com.portfolio.user.entity.User;
import com.portfolio.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seeder.SeederResult;

import java.util.Optional;

@Service
public class UserSeederService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserSeederService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public SeederResult processUser(User user) {
        SeederResult result = new SeederResult();
        Optional<User> existingRecord = userRepository.findUserByEmail(user.getEmail());

        if (existingRecord.isEmpty()) {
            Optional<Role> role = roleRepository.findBySlugWithUsers(Roles.SUPER_ADMIN.get());
            role.ifPresent(user::addRole);
            userRepository.save(user);
            result.iAdded();
        } else {
            result.iSkipped();
        }

        return result;
    }
}
