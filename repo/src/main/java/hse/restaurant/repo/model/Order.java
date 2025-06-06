package hse.restaurant.repo.model;

import java.util.HashSet;

import hse.restaurant.repo.model.status.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private UUID id = UUID.randomUUID();

    private String clientEmail;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_dish",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "id"))
    private Set<Dish> dishes = new HashSet<Dish>();

    private Integer totalPrice = 0;

    private OrderStatus status = OrderStatus.ACCEPTED;
    public void calculateTotalPrice(){
        for(Dish dish : dishes){
            totalPrice+=dish.getPrice();
        }
    }
}

