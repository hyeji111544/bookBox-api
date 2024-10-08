package green.mtcoding.bookbox.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() throws Exception {
        User user = User.builder().name("ksh").build();
        em.persist(user);
        em.flush();
        em.clear();
    }


    @DisplayName("유저 등록 테스트")
    @Test
    public void saveUser_Test() throws Exception {
        //given
        User user = User.builder().name("ssar").build();

        String usersJson = objectMapper.writeValueAsString(user);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent", "유저 등록 테스트")
                .content(usersJson))

        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));

    }


    @DisplayName("유저 조회 테스트")
    @Transactional(readOnly = true)
    @Test
    public void findUser_Test() throws Exception {
        //given
        int id = 1;

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}",id)
                .header("User-Agent", "유저 조회 테스트"))


        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("ksh"));

    }


    @DisplayName("유저 수정 테스트")
    @Test
    public void chageUser_Test() throws Exception {
        //given
        int id = 1;
        User user = User.builder().id(id).name("hapssar").build();

        String userJson = objectMapper.writeValueAsString(user);

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("User-Agent", "유저 수정 테스트")
                .content(userJson))


        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("hapssar"));


    }


}