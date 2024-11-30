package seeder;

import com.portfolio.address.AddressAction;
import com.portfolio.common.Permission;
import com.portfolio.role.RolePermissionSet;

import java.util.HashMap;
import java.util.Map;

public class ModuleSeeder implements Seeder {

    private final Map<String, Class<? extends Permission>> data;

    public ModuleSeeder(){
        data = new HashMap<>();
        data.put("roles", RolePermissionSet.class);
        data.put("permissions", AddressAction.class);
    }

    public SeederResult seed(){
        for (Map.Entry<String, Class<? extends Permission>> entry : data.entrySet()) {
            String moduleName = entry.getKey();

        }
        printAllPermissions(data);

        return null;
    }

    public static void printAllPermissions(Map<String, Class<? extends Permission>> modules) {
        for (Map.Entry<String, Class<? extends Permission>> entry : modules.entrySet()) {
            String moduleName = entry.getKey();
            Class<? extends Permission> permissionSet = entry.getValue();

            System.out.println("Permissions for module: " + moduleName);

            for (Permission permission : permissionSet.getEnumConstants()) {
                System.out.println(permission.getAction());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ModuleSeeder seder = new ModuleSeeder();
        seder.seed();
    }
}
