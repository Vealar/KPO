package hse.restaurant.repo.model;

import hse.restaurant.repo.model.status.ConnectionStatus;
import hse.restaurant.repo.model.status.UserStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private String password;

    private UserStatus role;

    private ConnectionStatus status = ConnectionStatus.OFFLINE;
}
