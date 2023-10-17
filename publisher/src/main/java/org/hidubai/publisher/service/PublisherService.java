package org.hidubai.publisher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hidubai.publisher.constants.ErrorCode;
import org.hidubai.publisher.dto.PublisherRequest;
import org.hidubai.publisher.dto.PublisherResponse;
import org.hidubai.publisher.stratergy.QueueSelectionStrategy;
import org.hidubai.rabbitmq.dto.MQRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class PublisherService implements Publisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherService.class);
    private RabbitTemplate rabbitTemplate;

    private QueueSelectionStrategy queueSelectionStrategy;

    public PublisherService(RabbitTemplate rabbitTemplate, QueueSelectionStrategy queueSelectionStrategy) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueSelectionStrategy = queueSelectionStrategy;
    }

    @Override
    public PublisherResponse sendMessage(PublisherRequest publisherRequest) {
        List<String> selectQueue = queueSelectionStrategy.selectQueue(publisherRequest);
        LOGGER.info(MessageFormat.format("Publishing Message to Rabit MQ Exchange Name: {0}", "LeadsMQ"));
        LOGGER.info(MessageFormat.format("Message info: LeadId:{0}, Mode: {1}", publisherRequest.getLeadId(), publisherRequest.getCommunicationMode().name()));
        selectQueue.stream().forEach((queueName) -> {
            try {
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
                rabbitTemplate.convertAndSend("leads-direct-exchange", queueName, json);
            } catch (Exception exception) {
                LOGGER.error(MessageFormat.format("Error while Publishing the Message for lead_id:{0} : caused: {1}", publisherRequest.getLeadId(), exception.getMessage()));
                throw new AmqpException(exception.getMessage());
            }
        });

        return PublisherResponse.builder().lead_id(publisherRequest.getLeadId())
                .code(String.valueOf(ErrorCode.SUCCESS.getCode()))
                .message(ErrorCode.SUCCESS.name()).build();

    }


}
