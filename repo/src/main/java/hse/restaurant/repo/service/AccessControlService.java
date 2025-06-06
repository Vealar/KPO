package hse.restaurant.repo.service;

import hse.restaurant.repo.model.User;
import hse.restaurant.repo.model.status.UserStatus;
import hse.restaurant.repo.repository.UserRepository;
import hse.restaurant.repo.service.access.AccessHandler;
import hse.restaurant.repo.service.access.AuthenticationHandler;
import hse.restaurant.repo.service.access.AuthorizationHandler;
import lombok.Data;

@Data
public class AccessControlService {
    private final AccessHandler authenticationHandler;
    private final AccessHandler authorizationHandler;

    public AccessControlService(UserRepository userRepository) {
        this.authenticationHandler = new AuthenticationHandler();
        this.authorizationHandler = new AuthorizationHandler(userRepository);
    }
    public boolean hasAccessGuest(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User doesn't exist");
        }
        return authenticationHandler.checkAccess(user) && authorizationHandler.checkAccess(user);
    }
    public boolean hasAccessAdmin(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return hasAccessGuest(user) && user.getRole() == UserStatus.ADMIN;
    }
}