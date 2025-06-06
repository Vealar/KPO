package hse.restaurant.repo.service.access;


import hse.restaurant.repo.model.User;
import hse.restaurant.repo.model.status.ConnectionStatus;

public class AuthenticationHandler implements AccessHandler {
    public AuthenticationHandler(){
    }
    @Override
    public boolean checkAccess(User user) {
        return user.getStatus() == ConnectionStatus.ONLINE;
    }
}
