package org.hidubai.publisher.api;

import jakarta.validation.Valid;
import org.hidubai.publisher.dto.PublisherRequest;
import org.hidubai.publisher.dto.PublisherResponse;
import org.hidubai.publisher.service.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lead/publish")
public class PublisherApi {

    @Autowired
    private Publisher publisher;

    @PostMapping(produces = "application/json")
    public ResponseEntity<PublisherResponse> publish(@RequestBody @Valid PublisherRequest publisherRequest){
        PublisherResponse response = this.publisher.sendMessage(publisherRequest);
        return ResponseEntity.ok(response);
    }

}
