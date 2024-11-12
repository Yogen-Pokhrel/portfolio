package seeder;

import com.portfolio.role.RoleRepository;
import com.portfolio.role.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleSeeder implements Seeder{
    List<Role> roles;

    @Autowired
    RoleRepository roleRepository;

    public RoleSeeder() {
        roles = new ArrayList<>();
        roles.add(Role.builder().title("user").slug("CUSTOMER").build());
        roles.add(Role.builder().title("admin").slug("ADMIN").build());
    }

    @Override
    public SeederResult seed() {
        return DatabaseSeederService.seed("Role Seeder", roles, role -> {
            SeederResult result = new SeederResult();
            Role existingRole = roleRepository.findBySlug(role.getSlug());
            if (existingRole == null) {
                roleRepository.save(role);
                result.iAdded();
            } else {
                result.iSkipped();
            }
            return result;
        });
    }
}
