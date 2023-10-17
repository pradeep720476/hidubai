package org.hidubai.publisher.stratergy;

import org.hidubai.publisher.dto.PublisherRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MultipleQueueSelectionStrategy implements QueueSelectionStrategy {
    @Override
    public List<String> selectQueue(PublisherRequest publisherRequest) {
        List<String> selectedQueue = new ArrayList<>();
        if (Objects.nonNull(publisherRequest.getLeadEmailId()))
            selectedQueue.add("email-channel");
        if (Objects.nonNull(publisherRequest.getLeadMobileNumber()))
            selectedQueue.add("mobile-channel");

        return selectedQueue;
    }
}
