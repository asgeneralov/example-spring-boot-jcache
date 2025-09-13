package io.github.asgeneralov.example.spring.boot.jcache.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHelloWithCustomName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                        .param("name", "Алексей"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Привет, Алексей!")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("время:")));
    }

    @Test
    public void testHealth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/health"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Приложение работает нормально!"))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));
    }

    @Test
    public void testClearCache() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cache/clear"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Кеш helloCache очищен!"));
    }

}
