package org.hidubai.publisher.handler;

import org.hidubai.publisher.api.PublisherApi;
import org.hidubai.publisher.config.MQConnectionDetail;
import org.hidubai.publisher.service.PublisherService;
import org.hidubai.publisher.stratergy.MultipleQueueSelectionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@ExtendWith({MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.yml")
public class RabitMQExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherService publisherService;

    @Test
    public void testAmqpExceptionTest() throws Exception {
        doThrow(AmqpException.class).when(publisherService).sendMessage(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/lead/publish").with(csrf()).with(httpBasic("pradeep","admit"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        " \"lead_id\" :123,\n" +
                        " \"source\" : \"landing page\",\n" +
                        " \"lead_name\" : \"Fazil\",\n" +
                        " \"lead_mobile_number\" : \"(123)123-1234\",\n" +
                        "\"lead_email_id\":\"xsdf@s.com\",\n" +
                        " \"preferred_mobile_communication_mode\" : \"PUSH_NOTIFICATION\",\n" +
                        " \"lead_message\" : \"how are oyu bud tytessy\"\n" +
                        "}")).andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("50001"));
        ;
    }
}
