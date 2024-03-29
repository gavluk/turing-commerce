package ua.com.gavluk.turing.ecommerce;

import freemarker.template.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import ua.com.gavluk.turing.ecommerce.api.LoggingFilter;
import ua.com.gavluk.turing.ecommerce.core.repo.RepositoryMarker;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = RepositoryMarker.class)
public class CommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceApplication.class, args);
    }

    @Bean
    public AbstractRequestLoggingFilter loggingFilter() {
        return new LoggingFilter();
    }
}
