package ru.megafon.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.megafon.test.MegafonApplication;
import ru.megafon.test.domain.User;
import ru.megafon.test.repository.UserRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MegafonApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/user/")).andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        var user = new User();
        user.setId(1);
        user.setLogin("username");
        user.setPassword("password");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        var user = new User();
        user.setId(1);
        user.setLogin("username");
        user.setPassword("password");
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        var ow = mapper.writer().withDefaultPrettyPrinter();
        var requestJson = ow.writeValueAsString(user);
        mockMvc.perform(post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
        var argument = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argument.capture());
        assertThat(argument.getValue().getLogin(), is("username"));
    }

    @Test
    public void update() throws Exception {
        User user = new User();
        user.setId(1);
        user.setLogin("username");
        user.setPassword("password");
        User upduser = new User();
        upduser.setId(1);
        upduser.setLogin("upduser");
        upduser.setPassword("123456");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(upduser);
        this.mockMvc.perform(put("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argument.capture());
        assertThat(argument.getValue().getLogin(), is("upduser"));
    }

    @Test
    public void deleteId() throws Exception {
        User user = new User();
        user.setId(1);
        user.setLogin("username");
        user.setPassword("password");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        this.mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());
    }
}
