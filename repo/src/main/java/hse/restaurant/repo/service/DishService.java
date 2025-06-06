package hse.restaurant.repo.service;

import hse.restaurant.repo.repository.DishRepository;
import hse.restaurant.repo.model.Dish;
import hse.restaurant.repo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final UserRepository userRepository;

    private final AccessControlService accessControlService;

    @Autowired
    public DishService(DishRepository dishRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.accessControlService = new AccessControlService(userRepository);

    }

    public Dish addDishItem(Dish dish,String email) {
        if (!accessControlService.hasAccessAdmin(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged in to add a dish");
        }
        // Проверяем наличие имени, описания и цены
        if (dish.getName() == null || dish.getName().isEmpty()) {
            throw new IllegalArgumentException("Dish name cannot be empty");
        }
        if (dish.getDescription() == null || dish.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Dish description cannot be empty");
        }
        if (dish.getPrice() == null || dish.getPrice() <= 0) {
            throw new IllegalArgumentException("Dish price must be greater than zero");
        }

        // Сохраняем блюдо в базе данных
        return dishRepository.save(dish);
    }


    public void removeDishItem(Dish dish,String email) {
        if (!accessControlService.hasAccessAdmin(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged as Admin");
        }
        dishRepository.delete(dish);
    }

    public Dish updateDishItem(Dish dishNew, Dish dishOld,String email) {
        if (!accessControlService.hasAccessAdmin(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged as Admin");
        }
        dishNew.setId(dishOld.getId());
        return addDishItem(dishNew,email);
    }

    public Dish getById(UUID id,String email) {
        if (!accessControlService.hasAccessAdmin(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged as Admin");
        }
        return dishRepository.findById(id).orElse(null);
    }

    public List<Dish> getAll(String email) {
        if (!accessControlService.hasAccessAdmin(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged as Admin");
        }
        return dishRepository.findAll();
    }
}
