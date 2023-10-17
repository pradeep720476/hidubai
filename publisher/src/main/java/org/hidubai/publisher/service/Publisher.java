package org.hidubai.publisher.service;

import org.hidubai.publisher.dto.PublisherRequest;
import org.hidubai.publisher.dto.PublisherResponse;

public interface Publisher {

    public PublisherResponse sendMessage(PublisherRequest publisherRequest);
}
