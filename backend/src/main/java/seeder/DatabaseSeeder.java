package seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootApplication(
        exclude = {
                // Exclude the embedded server auto-configuration (no web server needed)
                org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration.class,

                // Exclude web MVC auto-configuration
                org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.class,

                // Exclude security auto-configuration (not needed for seeding)
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
        }
)
@EnableCaching
@EnableAsync
@ComponentScan(basePackages = {"com.portfolio", "seeder"})
public class DatabaseSeeder implements CommandLineRunner {

    private final List<Seeder> seeders;

    @Autowired
    public DatabaseSeeder(List<Seeder> seeders) {
        this.seeders = seeders;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DatabaseSeeder.class);
        application.setWebApplicationType(WebApplicationType.NONE);

        // Run application and close after seeding completed
        application.run(args).close();
    }

    @Override
    @Transactional
    public void run(String... args) {
        for (Seeder seeder : seeders) {
            SeederResult result = seeder.seed();
            System.out.println(result);
        }
    }
}
