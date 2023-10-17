package org.hidubai.publisher.handler;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RabitMQExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;


    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private MultipleQueueSelectionStrategy multipleQueueSelectionStrategy;


    @Mock
    private PublisherService rabbitMQPublishService;

    @Test
    public void testAmqpExceptionTest() throws Exception {
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
                        "}")).andExpect(MockMvcResultMatchers.status().isOk());
        ;
    }
}
