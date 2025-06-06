package hse.ru.hw4.controller;

import hse.ru.hw4.model.TodoListItem;
import hse.ru.hw4.service.TodoListServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoListControllerImpl implements TodoListController {
    private final TodoListServiceImpl todoListServiceImpl;

    public TodoListControllerImpl(TodoListServiceImpl todoListServiceImpl) {
        this.todoListServiceImpl = todoListServiceImpl;
    }

    @PostMapping("/add")
    public TodoListItem addTodoListItem(@RequestBody TodoListItem item) {
        return todoListServiceImpl.addNote(item);
    }

    @DeleteMapping("/delete")
    public void deleteTodoItem(@RequestParam String title) {
        todoListServiceImpl.deleteNote(title);
    }

    @GetMapping("/all")
    public List<TodoListItem> findAll() {
        return todoListServiceImpl.findAllNotes();
    }
}
