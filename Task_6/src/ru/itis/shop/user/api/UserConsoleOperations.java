package ru.itis.shop.user.api;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;

import java.util.List;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "3": {
                findUserById();
            }
            break;
            case "4": {
                updateProfileDescription();
            }
            break;
            case "5": {
                printAllUsers();
            }
            break;
            case "6": {
                findUsersByProfileDescription();
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить описание пользователя по почте");
        System.out.println("5. Получить информацию обо всех пользователях");
        System.out.println("6. Показать информацию о пользователях с заданным описанием профиля");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите name:");
        String name = scanner.nextLine();
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(name, email, password, profileDescription);
    }


    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.out.println("Email или пароль не верны");
        }
    }

    private void findUserById() {
        System.out.println("Введите id пользователя: ");
        try {
            Integer id = scanner.nextInt();
            scanner.nextLine();
            UserDto user = userService.findUserById(id);
            System.out.println(user.getId() + " | "
            + user.getEmail() + " | "
            + user.getProfileDescription());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateProfileDescription() {
        System.out.println("Введите email пользователя: ");
        String email = scanner.nextLine();
        System.out.println("Введите новое описание профиля: ");
        String newProfileDescription = scanner.nextLine();
        try {
            userService.updateProfileDescriptionByEmail(email, newProfileDescription);
            System.out.println("Обновление описания профиля прошло успешно!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printAllUsers() {
        List<UserDto> users = userService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("Пользователей пока нет");
        } else {
            System.out.println("Пользователи: ");
            for (UserDto user : users) {
                System.out.println(user.getId() + " | "
                        + user.getEmail() + " | "
                        + user.getProfileDescription());
            }
        }
    }

    private void findUsersByProfileDescription() {
        System.out.println("Введите описание профиля: ");
        String profileDescription = scanner.nextLine();
        List<UserDto> users = userService.getUsersByProfileDescription(profileDescription);
        if (users.isEmpty()) {
            System.out.println("Пользователей с описание профиля " + profileDescription + " не найдено");
        } else {
            System.out.println("Пользователи с описанием профиля " + profileDescription + ": ");
            for (UserDto user : users) {
                System.out.println(user.getId() + " | "
                        + user.getEmail());
            }
        }
    }
}
