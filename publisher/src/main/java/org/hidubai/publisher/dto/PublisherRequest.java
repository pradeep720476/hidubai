package org.hidubai.publisher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hidubai.publisher.validators.EnumValue;
import org.hidubai.rabbitmq.constant.CommunicationType;

import java.io.Serializable;
import java.util.Objects;

/**
 * TODO: @assumption International Format of USA /^\([0-9]{3}\)[0-9]{3}-[0-9]{4}$/ format: (123)123-1234
 */

@Data
@Builder(toBuilder = true)
public class PublisherRequest implements Serializable {
    private static final long serialVersionUID = 123456789L;
    @JsonProperty("lead_id")
    @NotNull(message = "lead id cannot be null or must be positive number without decimal")
    @Min(message = "lead id cannot be null or must be positive number without decimal", value = 0)
    @Max(message = "lead id cannot be null or must be positive number without decimal", value = Integer.MAX_VALUE)
    private Integer leadId;
    @JsonProperty("source")
    private String source;

    @JsonProperty("lead_name")
    private String leadName;
    @JsonProperty("lead_mobile_number")
    @Pattern(regexp = "^\\([0-9]{3}\\)[0-9]{3}-[0-9]{4}$", message = "Invalid international lead_mobile_number format: ex: (123)123-1234")
    private String leadMobileNumber;
    @JsonProperty("lead_email_id")
    @Email(message = "Invalid email id formate ex : anything@dmain.com")
    private String leadEmailId;

    @JsonProperty("preferred_mobile_communication_mode")
    //@EnumValue(enumClass = CommunicationType.class)
    private CommunicationType communicationMode;

    @JsonProperty("lead_message")
    @NotNull(message = "lead_message is mandatory")
    @NotBlank(message = "lead_message is mandatory")
    @Size(message = "lead_message should be more than 10 character", min = 10)
    private String leadMessage;


    @AssertTrue(message = "lead_mobile_number or lead_email_id is required")
    public boolean isAtLeastEmailorMobileNumber() {
        return Objects.nonNull(this.leadEmailId) && !leadEmailId.isEmpty() ||
                Objects.nonNull(this.leadMobileNumber) && !leadMobileNumber.isEmpty();
    }
}
