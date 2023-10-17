package org.hidubai.publisher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hidubai.publisher.dto.PublisherRequest;
import org.hidubai.publisher.stratergy.MultipleQueueSelectionStrategy;
import org.hidubai.rabbitmq.dto.MQRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@SpringJUnitConfig
public class PublisherServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private MultipleQueueSelectionStrategy multipleQueueSelectionStrategy;

    @MockBean
    private PublisherService rabbitMQPublishService;


    @BeforeEach
    public void setUp() {
        rabbitMQPublishService = new PublisherService(rabbitTemplate, multipleQueueSelectionStrategy);
    }

    @Test
    public void testPublishMessage() throws IOException {
        when(multipleQueueSelectionStrategy.selectQueue(any())).thenReturn(Arrays.asList("email-channel", "mobile-channel"));
        // Define your message payload
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:publisherapi-test.json");
        ObjectMapper mapper = new ObjectMapper();
        PublisherRequest publisherRequest = mapper.readValue(resource.getInputStream(), PublisherRequest.class);
        MQRequest request = MQRequest.builder()
                .mode(publisherRequest.getCommunicationMode().name()).leadId(publisherRequest.getLeadId())
                .leadMessage(publisherRequest.getLeadMessage())
                .communicationMode(publisherRequest.getCommunicationMode())
                .leadName(publisherRequest.getLeadName())
                .source(publisherRequest.getSource())
                .leadEmailId(publisherRequest.getLeadEmailId()).leadMobileNumber(publisherRequest.getLeadMobileNumber())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);
        // Call the publish method on your service
        rabbitMQPublishService.sendMessage(publisherRequest);
        // Verify that the message was sent to the correct exchange and routing key
        verify(rabbitTemplate).convertAndSend("leads-direct-exchange", "email-channel", json);
    }
}
