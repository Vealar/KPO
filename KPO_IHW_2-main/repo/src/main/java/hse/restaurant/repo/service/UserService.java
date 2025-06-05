package hse.restaurant.repo.service;

import hse.restaurant.repo.model.status.ConnectionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hse.restaurant.repo.model.User;
import hse.restaurant.repo.repository.UserRepository;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccessControlService accessControlService;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.accessControlService = new AccessControlService(userRepository);
    }

    public User registerUser(User user) {
        // Проверяем корректность пароля
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        // Проверяем корректность имени
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        // Проверяем корректность email
        if (user.getEmail() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        // Проверяем корректность статуса
        if (user.getStatus() == null ) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        // Проверяем, что пользователь с таким email не существует
        if (accessControlService.getAuthorizationHandler().checkAccess(user)) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }

        // Сохранение пользователя в репозитории
        return userRepository.save(user);
    }


    public User login(User user) {
        // Поиск пользователя по электронной почте
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            throw new RuntimeException("User with email " + user.getEmail() + " not found");
        }
        if (!Objects.equals(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        if (existingUser.getStatus() == ConnectionStatus.ONLINE) {
            throw new RuntimeException("User with email " + user.getEmail() + " is already logged in");
        }
        user.setStatus(ConnectionStatus.ONLINE);
        user.setId(existingUser.getId());
        userRepository.save(user);
        return user;
    }

    public User logout(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            throw new RuntimeException("User with email " + user.getEmail() + " not found");
        }
        if (existingUser.getStatus() == ConnectionStatus.OFFLINE) {
            throw new RuntimeException("User with email " + user.getEmail() + " is already logged out");
        }
        user.setStatus(ConnectionStatus.OFFLINE);
        user.setId(existingUser.getId());
        userRepository.save(user);
        return user;
    }


}
