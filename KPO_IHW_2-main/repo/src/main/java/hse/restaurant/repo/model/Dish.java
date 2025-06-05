package hse.restaurant.repo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "cuisine")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name = null;

    private Integer price = null;

    private String description = null;

    private Integer time = null;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_dish",
            joinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"))
    private Set<Order> orders = new HashSet<Order>();
}
