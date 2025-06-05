package hse.ru.hw4.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TodoListItem {
    @Id
    private String title = null;
    private String description = null;
    public TodoListItem(){
    }
    public TodoListItem(String title,String description){
        this.title = title;
        this.description = description;
    }
}
