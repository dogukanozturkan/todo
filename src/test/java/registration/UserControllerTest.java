package registration;

import com.iyzico.Application;
import com.iyzico.controller.TodoController;
import com.iyzico.controller.UserController;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
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
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates/");
        viewResolver.setSuffix(".html");

        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).setViewResolvers(viewResolver).build();

    }

    @Test
    public void given_whenIndex_ThenReturnUserLoginMvcView() throws Exception {

        mockMvc.perform(
                get("/")
        )
                .andExpect(view().name("userLogin"));
    }

    @Test
    public void given_whenHomePage_ThenReturnUserLoginMvcView() throws Exception {

        mockMvc.perform(
                get("/")
        )
                .andExpect(view().name("userLogin"));
    }

    @Test
    public void given_whenUserRegister_ThenReturnUserRegisterMvcViewAndUserModelAttribute() throws Exception {

        mockMvc.perform(
                post("/userRegister")
        )
                .andExpect(view().name("userRegister"));
    }

    @Test
    public void givenUser_whenRegister_ThenReturnUserViewAndTodosModelAttribute() throws Exception {

        User user = new User();
        user.setEmail("dogukanozturkan@gmail.com");
        user.setFirstName("Dogukan");
        user.setLastName("Ozturkan");

        Mockito.doNothing().when(userService).register(any());

        mockMvc.perform(
                post("/register")
                        .requestAttr("todo", user)
        )
                .andExpect(view().name("redirect:/login"));

        verify(userService).register(any());
    }

}

