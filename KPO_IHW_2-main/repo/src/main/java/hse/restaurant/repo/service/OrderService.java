package hse.restaurant.repo.service;

import hse.restaurant.repo.model.Dish;
import hse.restaurant.repo.model.Order;
import hse.restaurant.repo.model.status.OrderStatus;
import hse.restaurant.repo.repository.DishRepository;
import hse.restaurant.repo.repository.OrderRepository;
import hse.restaurant.repo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;


    private final KitchenService kitchenService;
    private final AccessControlService accessControlService;


    @Autowired
    OrderService(OrderRepository orderRepository, DishRepository dishRepository, UserRepository userRepository){
        this.kitchenService = new KitchenService(orderRepository);
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.accessControlService = new AccessControlService(userRepository);
        this.userRepository = userRepository;

    }
    public Order create(Order order, String email) throws Exception {
        if (!accessControlService.hasAccessGuest(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged as Guest");
        }
        for (Dish dish : order.getDishes()) {
            UUID dishId = dish.getId();
            if (!dishRepository.existsById(dishId)) {
                throw new Exception("Some dish items are not available in the menu");
            }
        }

        order.calculateTotalPrice();
        order.setClientEmail(email);
        orderRepository.save(order);
        kitchenService.cookOrder(order);
        return order;
    }

    public void cancel(Order order, String email) {
        if (!accessControlService.hasAccessGuest(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged as Guest");
        }
        if (order == null) {
            throw new IllegalArgumentException("Impossible to cancel non-existing order");
        }
        if (!Objects.equals(order.getClientEmail(), email)) {
            throw new IllegalArgumentException("Order belongs to other user");
        }
        if (order.getStatus() != OrderStatus.PREPARING) {
            throw new IllegalStateException("It is impossible to cancel order that is not in process");
        }
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
    public String addDish(Dish dish, Order order, String email) {
        if (!accessControlService.hasAccessGuest(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged as Guest");
        }
        if (order == null) {
            throw new IllegalArgumentException("Impossible to update non-existing order");
        }
        if (!Objects.equals(order.getClientEmail(), email)) {
            throw new IllegalArgumentException("Order belongs to other user");
        }
        if (order.getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("It is impossible to add dish to cooked order");
        }
        if (!orderRepository.existsById(order.getId())) {
            throw new IllegalArgumentException("Impossible to update non-existent order");
        }
        if (dish == null) {
            throw new IllegalArgumentException("Dish cannot be null");
        }
        // Проверяем, существует ли блюдо
        if (!dishRepository.existsById(dish.getId())) {
            throw new IllegalArgumentException("Dish does not exist");
        }
        // Добавляем блюдо к списку блюд заказа
        order.getDishes().add(dish);
        // Обновляем общую стоимость заказа
        order.calculateTotalPrice();
        // Сохраняем обновленный заказ
        orderRepository.save(order);
        return "Dish successfully added to order";
    }

    public String getOrderStatus(UUID orderId, String email) {
        if (!accessControlService.hasAccessGuest(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged as Guest");
        }
        // Проверяем, что заказ существует
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " does not exist"));
        // Проверяем, что заказ принадлежит пользователю
        if (!Objects.equals(order.getClientEmail(), email)) {
            throw new IllegalArgumentException("Order belongs to other user");
        }
        return order.getStatus().name();
    }

    public String payCookedOrder(Order order, String email) {
        if (!accessControlService.hasAccessGuest(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged as Guest");
        }
        if (order == null) {
            throw new IllegalArgumentException("Unable to pay for non-existent order");
        }
        if (!Objects.equals(order.getClientEmail(), email)) {
            throw new IllegalArgumentException("Order belongs to other user");
        }
        if (order.getStatus() != OrderStatus.READY) {
            throw new IllegalStateException("Unable to pay for non-ready order");
        }
        order.setStatus(OrderStatus.PAYED);
        orderRepository.save(order);
        return "Order successfully paid";
    }


    public Order getById(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getByUserId(String email) {
        if (!accessControlService.hasAccessGuest(userRepository.findByEmail(email))) {
            throw new IllegalStateException("User must be logged as Guest");
        }
        return orderRepository.findByClientEmail(email);
    }

}