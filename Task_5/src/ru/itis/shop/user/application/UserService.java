package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String name, String email, String password, String profileDescription) {
        User user = new User(name, email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().equals(password);
        } else return false;
    }

    public void printAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println(user.getName() + " | " + user.getEmail() + " | " + user.getProfileDescription());
        }
    }

    public void printAllUsersByProfileDescription(String profileDescription) {
        List<User> users = userRepository.findByProfileDescription(profileDescription);
        for (User user : users) {
            System.out.println(user.getName() + " | " + user.getEmail());
        }
    }
}
