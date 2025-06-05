package hse.restaurant.repo.service.access;

import hse.restaurant.repo.model.User;

public interface AccessHandler {
    boolean checkAccess(User user);
}
