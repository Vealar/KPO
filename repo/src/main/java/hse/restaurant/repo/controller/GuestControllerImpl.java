package hse.restaurant.repo.controller;

import hse.restaurant.repo.model.Dish;
import hse.restaurant.repo.model.Order;
import hse.restaurant.repo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestControllerImpl implements GuestController {

        private final OrderService orderService;

        @PostMapping("/create")
        public ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestParam String email) {
                try {
                        Order createdOrder = orderService.create(order, email);
                        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
        }

        @PostMapping("/cancel")
        public ResponseEntity<String> cancelOrder(@RequestBody Order order, @RequestParam String email) {
                try {
                        orderService.cancel(order, email);
                        return ResponseEntity.ok("Order successfully canceled");
                } catch (IllegalArgumentException | IllegalStateException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                }
        }

        @PostMapping("/addDish")
        public ResponseEntity<String> addDishToOrder(@RequestBody Dish dish, @RequestBody Order order, @RequestParam String email) {
                try {
                        String message = orderService.addDish(dish, order, email);
                        return ResponseEntity.ok(message);
                } catch (IllegalArgumentException | IllegalStateException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                }
        }

        @GetMapping("/status")
        public ResponseEntity<String> getOrderStatus(@RequestBody Order order, @RequestParam String email) {
                try {
                        String status = orderService.getOrderStatus(order.getId(), email);
                        return ResponseEntity.ok(status);
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                }
        }

        @PostMapping("/pay")
        public ResponseEntity<String> payOrder(@RequestBody Order order, @RequestParam String email) {
                try {
                        String message = orderService.payCookedOrder(order, email);
                        return ResponseEntity.ok(message);
                } catch (IllegalArgumentException | IllegalStateException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
                Order order = orderService.getById(id);
                if (order != null) {
                        return ResponseEntity.ok(order);
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        @GetMapping("/user")
        public ResponseEntity<List<Order>> getOrdersByUserId(@RequestParam String email) {
                try {
                        List<Order> orders = orderService.getByUserId(email);
                        return ResponseEntity.ok(orders);
                } catch (IllegalStateException e) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // или любой другой HTTP-код состояния
                }
        }
}
