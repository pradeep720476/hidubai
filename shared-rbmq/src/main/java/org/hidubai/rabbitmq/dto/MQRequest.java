package org.hidubai.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hidubai.rabbitmq.constant.CommunicationType;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MQRequest implements Serializable {
    private String mode;
    private Integer leadId;
    private String source;

    private String leadName;
    private String leadMobileNumber;
    private String leadEmailId;
    private CommunicationType communicationMode;
    private String leadMessage;
}
