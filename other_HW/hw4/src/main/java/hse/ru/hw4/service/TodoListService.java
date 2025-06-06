package hse.ru.hw4.service;

import hse.ru.hw4.model.TodoListItem;

import java.util.List;

public interface TodoListService {
    TodoListItem addNote(TodoListItem item);

    void deleteNote(String title);

    List<TodoListItem> findAllNotes();
}
