package org.hidubai.publisher.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hidubai.publisher.dto.PublisherRequest;
import org.hidubai.publisher.dto.PublisherResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PublisherApiTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;




    @Test
    public void testApi() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:publisherapi-test.json");
        ObjectMapper mapper = new ObjectMapper();
        PublisherRequest publisherRequest = mapper.readValue(resource.getInputStream(), PublisherRequest.class);
        ResponseEntity<PublisherResponse> response = restTemplate.postForEntity("http://localhost:" + port + "/lead/publish", publisherRequest, PublisherResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS",response.getBody().getMessage());
    }

}
