package seeder.module;

import com.portfolio.common.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seeder.DatabaseSeederService;
import seeder.Seeder;
import seeder.SeederResult;

import java.util.List;
import java.util.Map;

@Component
public class ModuleSeeder implements Seeder {

    @Override
    public SeederResult seed(){
        List<Permission> permissions = PermissionUtils.getAllPermissionActions();
        for (Permission permission : permissions) {
            System.out.println("Action: " + permission.getAction() + ", Domain: " + permission.getDomain());
        }
//        return DatabaseSeederService.seed("Module Permission")
        return null;
    }
}
