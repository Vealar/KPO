package hse.ru.hw4.controller;

import hse.ru.hw4.model.TodoListItem;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface TodoListController {

    @PostMapping("/add")
    TodoListItem addTodoListItem(@RequestBody TodoListItem item);

    @DeleteMapping("/delete")
    void deleteTodoItem(@RequestParam String title);

    @GetMapping("/all")
    List<TodoListItem> findAll();
}
