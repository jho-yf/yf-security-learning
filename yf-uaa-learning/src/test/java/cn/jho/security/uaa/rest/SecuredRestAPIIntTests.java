package cn.jho.security.uaa.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * SecuredRestAPIIntTests
 *
 * @author JHO xu-jihong@qq.com
 * @date 2022-06-06 08:12
 */
@SpringBootTest
public class SecuredRestAPIIntTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                // 在构建MockMvc时应用SpringSecurity
                .apply(springSecurity())
                .build();
    }

    @Test
    // @WithMockUser模拟一个授权用户访问
    @WithMockUser
    public void givenAuthRequest_shouldSuccessWith200() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk());
    }

}
