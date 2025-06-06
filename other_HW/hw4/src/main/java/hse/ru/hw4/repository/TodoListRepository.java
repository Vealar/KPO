package hse.ru.hw4.repository;

import hse.ru.hw4.model.TodoListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TodoListRepository extends JpaRepository<TodoListItem, UUID> {
    TodoListItem findByTitle(String title);
}
