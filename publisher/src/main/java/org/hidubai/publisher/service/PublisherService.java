package org.hidubai.publisher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hidubai.publisher.config.MQConnectionDetail;
import org.hidubai.publisher.constants.Helper;
import org.hidubai.publisher.constants.HttpCode;
import org.hidubai.publisher.dto.Meta;
import org.hidubai.publisher.dto.PublisherRequest;
import org.hidubai.publisher.dto.PublisherResponse;
import org.hidubai.publisher.stratergy.QueueSelectionStrategy;
import org.hidubai.rabbitmq.constant.CommunicationType;
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

    private MQConnectionDetail mqConnectionDetail;

    public PublisherService(RabbitTemplate rabbitTemplate, QueueSelectionStrategy queueSelectionStrategy, MQConnectionDetail mqConnectionDetail) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueSelectionStrategy = queueSelectionStrategy;
        this.mqConnectionDetail = mqConnectionDetail;
    }

    @Override
    public PublisherResponse sendMessage(PublisherRequest publisherRequest) {
        List<String> selectQueue = queueSelectionStrategy.selectQueue(publisherRequest);
        LOGGER.info(MessageFormat.format("Publishing Message to Rabit MQ Exchange Name: {0}", "LeadsMQ"));
        LOGGER.debug(MessageFormat.format("Message info: LeadId:{0}, Mode: {1}", publisherRequest.getLeadId(), publisherRequest.getCommunicationMode()));
        selectQueue.stream().forEach((queueName) -> {
            try {
                MQRequest request = MQRequest.builder().leadId(publisherRequest.getLeadId())
                        .leadMessage(publisherRequest.getLeadMessage())
                        .communicationMode(CommunicationType.valueOf(publisherRequest.getCommunicationMode()))
                        .leadName(publisherRequest.getLeadName())
                        .source(publisherRequest.getSource())
                        .leadEmailId(publisherRequest.getLeadEmailId()).leadMobileNumber(publisherRequest.getLeadMobileNumber())
                        .build();
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(request);
                rabbitTemplate.convertAndSend(mqConnectionDetail.getExchangeName(), queueName, json);
            } catch (Exception exception) {
                LOGGER.error(MessageFormat.format("Error while Publishing the Message for lead_id:{0} : caused: {1}", publisherRequest.getLeadId(), exception.getMessage()));
                throw new AmqpException(exception.getMessage());
            }
        });

        return PublisherResponse.builder()
                .meta(Meta.builder()
                        .id(publisherRequest.getLeadId())
                        .code(String.valueOf(HttpCode.PUBLISHED.getCode()))
                        .message("Message has been published successfully.").build()).build();

    }

}
