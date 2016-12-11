package todo;

import com.iyzico.Application;
import com.iyzico.controller.TodoController;
import com.iyzico.dao.Todo;
import com.iyzico.dao.User;
import com.iyzico.repository.TodoRepository;
import com.iyzico.repository.UserRepository;
import com.iyzico.service.TodoService;
import com.iyzico.service.UserService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by flux on 11.12.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@WebAppConfiguration
public class TodoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    TodoController todoController;

    @Mock
    TodoService todoService;

    @Mock
    UserService userService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    private String sampleEmail = "dogukanozturkan@gmail.com";

    @Test
    public void givenLoggedInUser_whenUserDetails_ThenReturnUserViewAndTodosModelAttributeAndAssertEqualsEmail() throws Exception {
        List<Todo> todos = new ArrayList<>();

        User user = new User();

        user.setEmail("dogukanozturkan@gmail.com");
        user.setFirstName("Dogukan");
        user.setLastName("Ozturkan");

        when(userService.findByEmail(any())).thenReturn(user);

        mockMvc.perform(
                get("/secure/userDetail")
        )
                .andExpect(model().attribute("todos", equalTo(todos)))
                .andExpect(view().name("user"));

        Assert.assertEquals("Emails should be equals!", sampleEmail, user.getEmail());

    }

    @Test
    public void givenTodo_whenAddTodo_ThenReturnUserViewAndTodosModelAttribute() throws Exception {
        List<Todo> todos = new ArrayList<>();

        User user = new User();

        user.setEmail("dogukanozturkan@gmail.com");
        user.setFirstName("Dogukan");
        user.setLastName("Ozturkan");

        Todo todo = new Todo();
        todo.setUser(user);
        todo.setName("First Todo");

        when(userService.findByEmail(any())).thenReturn(user);
        when(todoService.findById(anyLong())).thenReturn(todo);

        mockMvc.perform(
                post("/secure/addTodo")
                        .requestAttr("todo",todo)
        )
                .andExpect(model().attribute("todos", todos))
                .andExpect(view().name("user"));

        verify(todoService).addTodo(any());
    }


    @Test
    public void givenTodoId_whenDeleteTodo_ThenReturnUserViewAndTodosModelAttribute() throws Exception {
        List<Todo> todos = new ArrayList<>();

        User user = new User();

        user.setEmail("dogukanozturkan@gmail.com");
        user.setFirstName("Dogukan");
        user.setLastName("Ozturkan");

        Todo todo = new Todo();
        todo.setUser(user);
        todo.setName("First Todo");

        when(userService.findByEmail(any())).thenReturn(user);

        mockMvc.perform(
                post("/secure/deleteTodo/1")
        )
                .andExpect(model().attribute("todos", todos))
                .andExpect(view().name("user"));

        verify(todoService).deleteTodo(any());

    }

    @Test
    public void givenTodoId_whenCompleteTodo_ThenReturnUserViewAndTodosModelAttributeAndReturnTrue() throws Exception {
        List<Todo> todos = new ArrayList<>();

        User user = new User();

        user.setEmail("dogukanozturkan@gmail.com");
        user.setFirstName("Dogukan");
        user.setLastName("Ozturkan");

        Todo todo = new Todo();
        todo.setUser(user);
        todo.setName("First Todo");
        todo.setCompleted(false);

        when(userService.findByEmail(any())).thenReturn(user);
        when(todoService.findById(anyLong())).thenReturn(todo);

        mockMvc.perform(
                post("/secure/completeTodo/1")
        )
                .andExpect(model().attribute("todos", todos))
                .andExpect(view().name("user"));

        verify(todoService).addTodo(any());

        Assert.assertEquals("Todo should be completed.",true,todo.isCompleted());
    }

}

