package seeder;

import com.portfolio.address.AddressPermissionSet;
import com.portfolio.common.PermissionSet;
import com.portfolio.role.RolePermissionSet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class ModuleSeeder implements Seeder {

    private final Map<String, Class<? extends PermissionSet>> data;

    public ModuleSeeder(){
        data = new HashMap<>();
        data.put("roles", RolePermissionSet.class);
        data.put("permissions", AddressPermissionSet.class);
    }

    public SeederResult seed(){
        for (Map.Entry<String, Class<? extends PermissionSet>> entry : data.entrySet()) {
            String moduleName = entry.getKey();

        }
        printAllPermissions(data);

        return null;
    }

    public static void printAllPermissions(Map<String, Class<? extends PermissionSet>> modules) {
        for (Map.Entry<String, Class<? extends PermissionSet>> entry : modules.entrySet()) {
            String moduleName = entry.getKey();
            Class<? extends PermissionSet> permissionSet = entry.getValue();

            System.out.println("Permissions for module: " + moduleName);

            for (PermissionSet permission : permissionSet.getEnumConstants()) {
                System.out.println(permission.get());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ModuleSeeder seder = new ModuleSeeder();
        seder.seed();
    }
}
