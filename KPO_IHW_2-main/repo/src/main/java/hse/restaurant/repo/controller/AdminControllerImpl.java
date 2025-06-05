package hse.restaurant.repo.controller;

import hse.restaurant.repo.model.Dish;
import hse.restaurant.repo.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminControllerImpl implements AdminController {

    private final DishService dishService;

    public AdminControllerImpl(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDish(@RequestBody Dish dish, @RequestParam String email) {
        try {
            Dish addedDish = dishService.addDishItem(dish, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedDish);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding dish: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeDish(@RequestParam UUID id, @RequestParam String email) {
        try {
            Dish dishToRemove = dishService.getById(id, email);
            if (dishToRemove != null) {
                dishService.removeDishItem(dishToRemove, email);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing dish: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDish(@RequestBody Dish updatedDish, @RequestParam UUID id, @RequestParam String email) {
        try {
            Dish existingDish = dishService.getById(id, email);
            if (existingDish != null) {
                updatedDish.setId(existingDish.getId());
                Dish updatedDishItem = dishService.updateDishItem(updatedDish, existingDish, email);
                return ResponseEntity.ok(updatedDishItem);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating dish: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDishById(@PathVariable UUID id, @RequestParam String email) {
        try {
            Dish dish = dishService.getById(id, email);
            if (dish != null) {
                return ResponseEntity.ok(dish);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting dish by id: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDishes(@RequestParam String email) {
        try {
            List<Dish> dishes = dishService.getAll(email);
            return ResponseEntity.ok(dishes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting all dishes: " + e.getMessage());
        }
    }
}


