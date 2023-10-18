package org.hidubai.publisher.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
public class PublisherApiTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lead/publish").with(csrf()).with(httpBasic("pradeep", "admit"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                " \"lead_id\" :123,\n" +
                                " \"source\" : \"landing page\",\n" +
                                " \"lead_name\" : \"Fazil\",\n" +
                                " \"lead_mobile_number\" : \"(123)123-1234\",\n" +
                                "\"lead_email_id\":\"xsdf@s.com\",\n" +
                                " \"preferred_mobile_communication_mode\" : \"PUSH_NOTIFICATION\",\n" +
                                " \"lead_message\" : \"how are oyu bud tytessy\"\n" +
                                "}")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));
    }

    @Test
    public void testUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lead/publish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                " \"lead_id\" :123,\n" +
                                " \"source\" : \"landing page\",\n" +
                                " \"lead_name\" : \"Fazil\",\n" +
                                " \"lead_mobile_number\" : \"(123)123-1234\",\n" +
                                "\"lead_email_id\":\"xsdf@s.com\",\n" +
                                " \"preferred_mobile_communication_mode\" : \"PUSH_NOTIFICATION\",\n" +
                                " \"lead_message\" : \"how are oyu bud tytessy\"\n" +
                                "}")).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}
