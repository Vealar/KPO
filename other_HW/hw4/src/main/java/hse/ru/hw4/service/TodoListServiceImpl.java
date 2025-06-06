package hse.ru.hw4.service;

import hse.ru.hw4.exception.DuplicateTitleException;
import hse.ru.hw4.exception.NotFoundItemException;
import hse.ru.hw4.exception.NullTitleException;
import hse.ru.hw4.model.TodoListItem;
import hse.ru.hw4.repository.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListServiceImpl implements TodoListService {
    private final TodoListRepository repository;

    public TodoListServiceImpl(TodoListRepository repository) {
        this.repository = repository;
    }

    public TodoListItem addNote(TodoListItem note) {
        if (note.getTitle() == null) {
            throw new NullTitleException("Title cannot be null");
        }

        if (repository.findByTitle(note.getTitle()) != null) {
            throw new DuplicateTitleException("Item with this title already exists");
        }
        return repository.save(note);
    }

    public void deleteNote(String title) {
        if (title == null) {
            throw new NullTitleException("Title cannot be null");
        }
        TodoListItem todoListItem = repository.findByTitle(title);
        if (todoListItem == null) {
            throw new NotFoundItemException("Item with title " + title + " not found");
        }
        repository.delete(todoListItem);
    }

    public List<TodoListItem> findAllNotes() {
        return repository.findAll();
    }
}
