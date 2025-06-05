package hse.restaurant.repo.controller;

import hse.restaurant.repo.model.Dish;
import hse.restaurant.repo.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/guest")
public interface GuestController {

    @PostMapping("/create")
    ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestParam String email);

    @PostMapping("/cancel")
    ResponseEntity<String> cancelOrder(@RequestBody Order order, @RequestParam String email);

    @PostMapping("/addDish")
    ResponseEntity<String> addDishToOrder(@RequestBody Dish dish, @RequestBody Order order, @RequestParam String email);

    @GetMapping("/status")
    ResponseEntity<String> getOrderStatus(@RequestBody Order order, @RequestParam String email);

    @PostMapping("/pay")
    ResponseEntity<String> payOrder(@RequestBody Order order, @RequestParam String email);

    @GetMapping("/{id}")
    ResponseEntity<Order> getOrderById(@PathVariable UUID id);

    @GetMapping("/user")
    ResponseEntity<List<Order>> getOrdersByUserId(@RequestParam String email);
}

