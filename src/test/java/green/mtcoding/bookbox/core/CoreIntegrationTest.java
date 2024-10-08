package green.mtcoding.bookbox.core;


import green.mtcoding.bookbox.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CoreIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;


    @DisplayName("잘못된 요청 테스트")
    @Transactional(readOnly = true)
    @Test
    public void requestWrongData_Test() throws Exception {
        //given
        String id = "cos";

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", id)
                .header("User-Agent", "잘못된 요청 테스트"))

        //then
        .andExpect(status().isBadRequest());

    }


    @DisplayName("존재하지 않는 API요청 테스트")
    @Transactional(readOnly = true)
    @Test
    public void requestWrongAPI_Test() throws Exception {
        //given


        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/board/")
                .header("User-Agent", "존재하지 않는 API요청 테스트"))

        //then
        .andExpect(status().isNotFound());

    }


    @DisplayName("필터 테스트")
    @Transactional
    @Test
    public void checkUrlFilter_Test() throws Exception {
        //given
        int id = 1;
        User user = User.builder().username("hapssar").build();
        em.persist(user);
        em.flush();

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}?name=test!!", id)
                .header("User-Agent", "필터 테스트"))

        //then
                .andExpect(status().isBadRequest());

    }
}
