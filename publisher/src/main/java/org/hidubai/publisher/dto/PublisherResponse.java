package org.hidubai.publisher.dto;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class PublisherResponse {
    private Integer lead_id;
    private String code;
    private String message;
}
