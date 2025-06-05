package hse.restaurant.repo.service.access;

import hse.restaurant.repo.model.User;
import hse.restaurant.repo.repository.UserRepository;

public class AuthorizationHandler implements AccessHandler {
    UserRepository userRepository;
    public AuthorizationHandler(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public boolean checkAccess(User user) {
        return userRepository.findByEmail(user.getEmail())!=null;
    }
}
