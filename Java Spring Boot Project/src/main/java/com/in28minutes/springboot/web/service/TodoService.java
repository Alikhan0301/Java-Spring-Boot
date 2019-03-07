
package com.in28minutes.springboot.web.service;

import java.util.List;

import com.in28minutes.springboot.web.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service()
@Component

public class TodoService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static int todoCount = 100;

    public List<Todo> retrieveTodos(String userName) {
        String sql = "SELECT * FROM todo_list";
        List<Todo> filteredTodos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Todo.class));
        return filteredTodos;
    }


    public Todo retrieveTodo(int id) {
        String sql = String.format("SELECT FROM todo_list WHERE id = %d", id);
        List<Todo> cur_todos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Todo.class));
        return cur_todos.get(0);
    }

    public void updateTodo(Todo todo){
        int id = todo.getId();
        deleteTodo(id);
        addTodo(todo.getUsername(), todo.getDescription(), todo.getTargetDate(), todo.getIsDone());
    }

    public void addTodo(String username, String description, String targetDate,
                        boolean isDone) {
        Todo cur = new Todo(++todoCount, username, description, targetDate, isDone);
        String sql = String.format("INSERT INTO todo_list (id, username, description, targetDate, isDone) VALUES('%s','%s','%s','%s','%s')", cur.getId(), cur.getUsername(), cur.getDescription(), cur.getTargetDate(), cur.getIsDone());
        jdbcTemplate.execute(sql);
    }

    public void deleteTodo(int id) {
        String sql = String.format("DELETE FROM todo_list WHERE id = %d",id);
        jdbcTemplate.execute(sql);
    }


}
