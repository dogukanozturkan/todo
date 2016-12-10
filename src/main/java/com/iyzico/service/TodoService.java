package com.iyzico.service;

import com.iyzico.dao.Todo;
import com.iyzico.dao.User;
import com.iyzico.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Date: 10/12/2016 Time: 13:00
 *
 * @author dogukan.ozturkan (https://github.com/dogukanozturkan)
 */

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo findById(long todoId) {
        return todoRepository.findOne(todoId);
    }

    public List<Todo> getTodos(User user) {
        return todoRepository.findAllByUser(user);
    }

    public void addTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public void deleteTodo(Todo todo) {
        todoRepository.deleteById(todo.getId());
    }
}
