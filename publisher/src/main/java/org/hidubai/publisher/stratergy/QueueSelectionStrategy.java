package org.hidubai.publisher.stratergy;

import org.hidubai.publisher.dto.PublisherRequest;

import java.util.List;

public interface QueueSelectionStrategy {
    List<String> selectQueue(PublisherRequest  publisherRequest);
}
