package org.hidubai.publisher.stratergy;

import org.hidubai.publisher.config.MQConnectionDetail;
import org.hidubai.publisher.dto.PublisherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MultipleQueueSelectionStrategy implements QueueSelectionStrategy {
    @Autowired
    private MQConnectionDetail mqConnectionDetail;

    @Override
    public List<String> selectQueue(PublisherRequest publisherRequest) {
        List<String> selectedQueue = new ArrayList<>();
        if (Objects.nonNull(publisherRequest.getLeadEmailId()))
            selectedQueue.add(mqConnectionDetail.getEmailChannelName());
        if (Objects.nonNull(publisherRequest.getLeadMobileNumber()))
            selectedQueue.add(mqConnectionDetail.getMobileChannelName());

        return selectedQueue;
    }
}
