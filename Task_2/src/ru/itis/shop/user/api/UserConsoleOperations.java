package ru.itis.shop.user.api;

import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;

import java.util.Optional;
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
                findById();
            }
            break;
            case "4": {
                updateUser();
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
        System.out.println("4. обновить данные пользователя");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(email, password, profileDescription);
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

    private void findById() {
        System.out.println("Ищем пользователя по id");
        System.out.println("Введите id требуемого пользователя: ");
        String id = scanner.nextLine();

        if (userService.findById(id).get() != null) {
            System.out.println("Пользователь с данным id найден, email - " + userService.findById(id).get().getEmail());
        } else {
            System.out.println("Пользователя с даннным id не существует");
        }
    }

    private void updateUser() {
        System.out.println("Обновляем данные пользователя");
        System.out.println("Введите email пользователя: ");
        String email = scanner.nextLine();
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.get() != null) {
            System.out.println("Пользователь с данным email найден");
            System.out.println("Обновляем описание профиля");
            System.out.println("Введите новое описание профиля: ");
            String profileDescription = scanner.nextLine();
            if (userService.updateUser(userOptional.get(), profileDescription)) {
                System.out.println("Обновление описания профиля прошло успешно");
            } else {
                System.out.println("Не удалось обновить описание профиля");
            }
        } else {
            System.out.println("Пользователь с двнным email не найден");
        }
    }

}
