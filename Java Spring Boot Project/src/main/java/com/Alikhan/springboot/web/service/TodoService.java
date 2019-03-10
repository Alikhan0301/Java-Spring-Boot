
package com.Alikhan.springboot.web.service;

import java.util.List;

import com.Alikhan.springboot.web.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service()
@Component

@CacheConfig(cacheNames = "Todos")


public class TodoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Cacheable("Cachefiles")
    public List<Todo> retrieveTodos(String userName) {
        String sql = "SELECT * FROM todo_list";
        List<Todo> filteredTodos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Todo.class));
        simulateSlowService();
        return filteredTodos;
    }


    public Todo retrieveTodo(int id) {
        String sql = String.format("SELECT FROM todo_list WHERE id = %d", id);
        List<Todo> cur_todos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Todo.class));
        return cur_todos.get(0);
    }
    @CachePut
    public void updateTodo(Todo todo){
        int id = todo.getId();
        deleteTodo(id);
        addTodo(todo.getUsername(), todo.getDescription(), todo.getTargetDate(), todo.getIsDone());
    }
    @CachePut(value = "Todos")
    public void addTodo(String username, String description, String targetDate,
                        boolean isDone) {
        Random rand = new Random();
        int rnd = rand.nextInt(Integer.MAX_VALUE);
        Todo cur = new Todo(rnd, username, description, targetDate, isDone);
        String sql = String.format("INSERT INTO todo_list (id, username, description, targetDate, isDone) VALUES('%s','%s','%s','%s','%s')", cur.getId(), cur.getUsername(), cur.getDescription(), cur.getTargetDate(), cur.getIsDone());
        jdbcTemplate.execute(sql);
    }
    @CachePut(value = "Todos")
    public void deleteTodo(int id) {
        String sql = String.format("DELETE FROM todo_list WHERE id = %d",id);
        jdbcTemplate.execute(sql);
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
