package org.example.ru.itis.shop.app;

import org.example.ru.itis.shop.infrastructure.persistence.jdbc.DriverManagerDataSource;
import org.example.ru.itis.shop.user.api.UserConsoleOperations;
import org.example.ru.itis.shop.user.application.UserService;
import org.example.ru.itis.shop.user.infrastrcture.persistence.jdbc.UserRepositoryJdbcImpl;
import org.example.ru.itis.shop.user.repository.UserRepository;
import org.example.ru.itis.shop.util.PropertiesReader;

import javax.sql.DataSource;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        PropertiesReader propertiesReader = new PropertiesReader("application.properties");
        Properties properties = propertiesReader.loadProperties();

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        DataSource dataSource = new DriverManagerDataSource(url, user, password);

        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);
        UserService userService = new UserService(userRepository);
        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}