package org.hidubai.publisher.stratergy;

import org.hidubai.publisher.dto.PublisherRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application-test.yml")
public class MultipleQueueSelectionStrategyTest {

    @Autowired
    private MultipleQueueSelectionStrategy multipleQueueSelectionStrategy;

    @Test
    public void getEmailQueueStrategyTest() {
        List<String> strings = multipleQueueSelectionStrategy.selectQueue(PublisherRequest.builder()
                .leadEmailId("x@test.com")
                .build());
        Assertions.assertEquals(1,strings.size());
        Assertions.assertEquals("email-channel",strings.get(0));

    }

    @Test
    public void getMobileQueueStrategyTest() {
        List<String> strings = multipleQueueSelectionStrategy.selectQueue(PublisherRequest.builder()
                .leadMobileNumber("1234")
                .build());
        Assertions.assertEquals(1,strings.size());
        Assertions.assertEquals("mobile-channel",strings.get(0));

    }

    @Test
    public void getBothQueueStrategyTest() {
        List<String> strings = multipleQueueSelectionStrategy.selectQueue(PublisherRequest.builder()
                .leadEmailId("x@test.com").leadMobileNumber("12345")
                .build());
        Assertions.assertEquals(2,strings.size());
        Assertions.assertTrue(strings.contains("email-channel"));
        Assertions.assertTrue(strings.contains("mobile-channel"));

    }

}
