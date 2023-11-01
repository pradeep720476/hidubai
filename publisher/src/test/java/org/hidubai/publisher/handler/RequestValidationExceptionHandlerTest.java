package org.hidubai.publisher.handler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.yml")
public class RequestValidationExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void handleMethodArgumentNotValidExceptionLeadIdNullTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lead/publish").with(httpBasic("hidubai","admit"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                " \"lead_id\" :null,\n" +
                                " \"source\" : \"landing page\",\n" +
                                " \"lead_name\" : \"Fazil\",\n" +
                                " \"lead_mobile_number\" : \"(123)123-1234\",\n" +
                                " \"preferred_mobile_communication_mode\" : \"PUSH_NOTIFICATION\",\n" +
                                " \"lead_message\" : \"how are oyu bud tytessy\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("lead id cannot be empty or must be positive number without decimal"));
    }

    @Test
    public void handleMethodArgumentNotValidExceptionMobileOrEmailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lead/publish").with(httpBasic("hidubai","admit"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                " \"lead_id\" :123,\n" +
                                " \"source\" : \"landing page\",\n" +
                                " \"lead_name\" : \"Fazil\",\n" +
                                " \"preferred_mobile_communication_mode\" : \"PUSH_NOTIFICATION\",\n" +
                                " \"lead_message\" : \"how are oyu bud tytessy\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("lead_mobile_number or lead_email_id is required"));
    }

    @Test
    public void handleMethodArgumentNotValidExceptionMobileFormatTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lead/publish").with(httpBasic("hidubai","admit"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                " \"lead_id\" :123,\n" +
                                " \"source\" : \"landing page\",\n" +
                                " \"lead_name\" : \"Fazil\",\n" +
                                " \"lead_mobile_number\" : \"(123123-1234\",\n" +
                                " \"preferred_mobile_communication_mode\" : \"PUSH_NOTIFICATION\",\n" +
                                " \"lead_message\" : \"how are oyu bud tytessy\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("Invalid international lead_mobile_number format: ex: (123)123-1234"));
    }

    @Test
    public void handleMethodArgumentNotValidExceptionEmailFormatTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lead/publish").with(httpBasic("hidubai","admit"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                " \"lead_id\" :123,\n" +
                                " \"source\" : \"landing page\",\n" +
                                " \"lead_name\" : \"Fazil\",\n" +
                                " \"lead_mobile_number\" : \"(123)123-1234\",\n" +
                                "\"lead_email_id\":\"xsdf\",\n" +
                                " \"preferred_mobile_communication_mode\" : \"PUSH_NOTIFICATION\",\n" +
                                " \"lead_message\" : \"how are oyu bud tytessy\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("Invalid email id formate ex : anything@domain.com"));
    }


    @Test
    public void handleMethodArgumentNotValidExceptionModeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lead/publish").with(httpBasic("hidubai","admit"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                " \"lead_id\" :123,\n" +
                                " \"source\" : \"landing page\",\n" +
                                " \"lead_name\" : \"Fazil\",\n" +
                                " \"lead_mobile_number\" : \"(123)123-1234\",\n" +
                                "\"lead_email_id\":\"xsdf\",\n" +
                                " \"preferred_mobile_communication_mode\" : \"TEST\",\n" +
                                " \"lead_message\" : \"how are oyu bud tytessy\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].code").value("VALIDATION_ERROR_10001"));
    }
}
