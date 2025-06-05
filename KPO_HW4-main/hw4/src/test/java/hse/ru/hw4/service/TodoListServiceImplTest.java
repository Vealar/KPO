package hse.ru.hw4.service;

import hse.ru.hw4.exception.DuplicateTitleException;
import hse.ru.hw4.exception.NotFoundItemException;
import hse.ru.hw4.exception.NullTitleException;
import hse.ru.hw4.model.TodoListItem;
import hse.ru.hw4.repository.TodoListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoListServiceImplTest  {
    @InjectMocks
    private TodoListServiceImpl todoListService;
    @Mock
    private TodoListRepository todoListRepository;

    @Test
    void addNote_simpleAdd_savesNote(){
        TodoListItem item1 = new TodoListItem("task1","desc1");
        TodoListItem item2 = new TodoListItem("task2","desc2");

        when(todoListRepository.save(item1)).thenReturn(item1);
        when(todoListRepository.save(item2)).thenReturn(item2);

        TodoListItem item1_saved = todoListService.addNote(item1);
        TodoListItem item2_saved = todoListService.addNote(item2);

        assertEquals(item1,item1_saved);
        assertEquals(item2,item2_saved);

    }
    @Test
    void addNote_addDuplicate_throwsException(){
        TodoListItem item1 = new TodoListItem("task1","desc1");

        when(todoListRepository.findByTitle(item1.getTitle())).thenReturn(item1);

        assertThrows(DuplicateTitleException.class,()->todoListService.addNote(item1));
    }

    @Test
    void addNote_addNullTitle_throwsException(){
        TodoListItem item1 = new TodoListItem(null,"desc1");

        assertThrows(NullTitleException.class,()->todoListService.addNote(item1));
    }
    @Test
    void deleteNote_simpleDelete_success(){
        TodoListItem item1 = new TodoListItem("task1","desc1");
        String deleteTitle = item1.getTitle();

        when(todoListRepository.findByTitle(item1.getTitle())).thenReturn(item1);

        todoListService.deleteNote(deleteTitle);

        verify(todoListRepository, times(1)).delete(item1);
    }
    @Test
    void deleteNote_deleteNullTitle_throwsException(){
        TodoListItem item1 = new TodoListItem(null,"desc1");

        assertThrows(NullTitleException.class,()->todoListService.deleteNote(item1.getTitle()));
    }
    @Test
    void deleteNote_deleteNotExistTitle_throwsException(){
        String nonExistingTitle = "NonExistingTitle";

        when(todoListRepository.findByTitle(nonExistingTitle)).thenReturn(null);

        assertThrows(NotFoundItemException.class, () -> todoListService.deleteNote(nonExistingTitle));
    }
    @Test
    void findAllNotes_simpleTodoList_success() {
        TodoListItem item1 = new TodoListItem("task1","desc1");
        TodoListItem item2 = new TodoListItem("task2","desc2");
        TodoListItem item3 = new TodoListItem("task3","desc3");

        List<TodoListItem> todoListItems = List.of(item1,item2,item3);
        when(todoListRepository.findAll()).thenReturn(todoListItems);
        List<TodoListItem> result = todoListService.findAllNotes();

        assertEquals(todoListItems, result);
    }
}
