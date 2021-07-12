package nl.cinqict;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String POST_URL = "/post";
    private static final String GET_URL = "/";

    @AfterEach
    public void reset() {
        post("/reset", "");
    }

    @Test
    public void happyFlowEmptyList() {
        get(GET_URL, "[]");
    }

    @Test
    public void happyFlowSingleItem() throws Exception {
        post(POST_URL, "a");
        get(GET_URL, "[a]");
    }

    @Test
    public void happyFlowSameItem() throws Exception {
        post(POST_URL, "a");
        post(POST_URL, "a");
        get(GET_URL, "[a]");
    }

    @Test
    public void happyFlowDoubleItem() throws Exception {
        post(POST_URL, "a");
        post(POST_URL, "b");
        get(GET_URL, "[a, b]");
    }

    private void post(String url, String body) {
        try {
            mockMvc.perform(MockMvcRequestBuilders.post(url).content(body)).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private void get(String url, String expectedResponse) {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(url))
                    .andExpect(status().isOk())
                    .andExpect(content().string(equalTo(expectedResponse)));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}