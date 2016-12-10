package com.iyzico.controller;

import com.iyzico.dao.Todo;
import com.iyzico.dao.User;
import com.iyzico.service.TodoService;
import com.iyzico.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

/**
 * Date: 10/12/2016 Time: 13:00
 *
 * @author dogukan.ozturkan (https://github.com/dogukanozturkan)
 */
@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/secure/userDetail")
    public String userDetail(Model model, WebRequest request) {

        User user = userService.findByEmail(request.getRemoteUser());

        model.addAttribute("todo", new Todo());
        model.addAttribute("todos", todoService.getTodos(user));
        return "user";
    }

    @RequestMapping(value = "/secure/addTodo")
    public String addTodo(@Valid @ModelAttribute("todo") Todo todo, Model model,
                          WebRequest request) {
        User user = userService.findByEmail(request.getRemoteUser());
        todo.setCompleted(false);
        todo.setUser(user);
        todoService.addTodo(todo);

        model.addAttribute("todo", new Todo());
        model.addAttribute("todos", todoService.getTodos(user));
        return "user";
    }

    @RequestMapping(value = "/secure/deleteTodo/{todoId}",method = RequestMethod.POST)
    public String deleteTodo(@PathVariable long todoId, Model model,
                             WebRequest request) {

        User user = userService.findByEmail(request.getRemoteUser());
        todoService.deleteTodo(todoService.findById(todoId));

        model.addAttribute("todo", new Todo());
        model.addAttribute("todos", todoService.getTodos(user));
        return "user";
    }

    @RequestMapping(value = "/secure/completeTodo/{todoId}",method = RequestMethod.POST)
    public String completeTodo(@PathVariable long todoId, Model model,
                             WebRequest request) {

        User user = userService.findByEmail(request.getRemoteUser());
        Todo todo = todoService.findById(todoId);
        todo.setCompleted(true);
        todoService.addTodo(todo);

        model.addAttribute("todo", new Todo());
        model.addAttribute("todos", todoService.getTodos(user));
        return "user";
    }

}
