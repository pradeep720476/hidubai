package org.hidubai.publisher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hidubai.publisher.config.MQConnectionDetail;
import org.hidubai.publisher.dto.PublisherRequest;
import org.hidubai.publisher.stratergy.MultipleQueueSelectionStrategy;
import org.hidubai.rabbitmq.constant.CommunicationType;
import org.hidubai.rabbitmq.dto.MQRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.io.IOException;

import static org.mockito.Mockito.verify;

@SpringBootTest
@TestPropertySource("classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MultipleQueueSelectionStrategy multipleQueueSelectionStrategy;

    private PublisherService publisherService;

    @Autowired
    private MQConnectionDetail mqConnectionDetail;

    @BeforeEach
    public void setUp() {
        publisherService = new PublisherService(rabbitTemplate, multipleQueueSelectionStrategy, mqConnectionDetail);
    }

    @Test
    public void testPublishEmailQueue() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:publisherapi-email-test.json");
        ObjectMapper mapper = new ObjectMapper();
        PublisherRequest publisherRequest = mapper.readValue(resource.getInputStream(), PublisherRequest.class);
        MQRequest request = MQRequest.builder()
                .leadId(publisherRequest.getLeadId())
                .leadMessage(publisherRequest.getLeadMessage())
                .communicationMode(CommunicationType.valueOf(publisherRequest.getCommunicationMode()))
                .leadName(publisherRequest.getLeadName())
                .source(publisherRequest.getSource())
                .leadEmailId(publisherRequest.getLeadEmailId()).leadMobileNumber(publisherRequest.getLeadMobileNumber())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);
        // Call the publish method on your service
        publisherService.sendMessage(publisherRequest);
        // Verify that the message was sent to the correct exchange and routing key
        verify(rabbitTemplate).convertAndSend("leads-direct-exchange", "email-channel", json);
    }


    @Test
    public void testPublishBothQueueMessageTest() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:publisherapi-both-test.json");
        ObjectMapper mapper = new ObjectMapper();
        PublisherRequest publisherRequest = mapper.readValue(resource.getInputStream(), PublisherRequest.class);
        MQRequest request = MQRequest.builder()
                .leadId(publisherRequest.getLeadId())
                .leadMessage(publisherRequest.getLeadMessage())
                .communicationMode(CommunicationType.valueOf(publisherRequest.getCommunicationMode()))
                .leadName(publisherRequest.getLeadName())
                .source(publisherRequest.getSource())
                .leadEmailId(publisherRequest.getLeadEmailId()).leadMobileNumber(publisherRequest.getLeadMobileNumber())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);
        publisherService.sendMessage(publisherRequest);
        verify(rabbitTemplate).convertAndSend("leads-direct-exchange", "email-channel", json);
        verify(rabbitTemplate).convertAndSend("leads-direct-exchange", "mobile-channel", json);
    }

    @Test
    public void testPublishMobileQueueMessageTest() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:publisherapi-mobile-test.json");
        ObjectMapper mapper = new ObjectMapper();
        PublisherRequest publisherRequest = mapper.readValue(resource.getInputStream(), PublisherRequest.class);
        MQRequest request = MQRequest.builder()
                .leadId(publisherRequest.getLeadId())
                .leadMessage(publisherRequest.getLeadMessage())
                .communicationMode(CommunicationType.valueOf(publisherRequest.getCommunicationMode()))
                .leadName(publisherRequest.getLeadName())
                .source(publisherRequest.getSource())
                .leadEmailId(publisherRequest.getLeadEmailId()).leadMobileNumber(publisherRequest.getLeadMobileNumber())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);
        publisherService.sendMessage(publisherRequest);
        verify(rabbitTemplate).convertAndSend("leads-direct-exchange", "mobile-channel", json);
    }
}
