package seeder.module;


import com.portfolio.common.Permission;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionUtils {
    public static List<Permission> getAllPermissionActions() {
        Reflections reflections = new Reflections("com.portfolio");
        List<Permission> actions = new ArrayList<>();

        reflections.getSubTypesOf(Permission.class).forEach(enumClass -> {
            if (enumClass.isEnum()) {
                actions.addAll(Arrays.asList(enumClass.getEnumConstants()));
            }
        });

        return actions;
    }
}
