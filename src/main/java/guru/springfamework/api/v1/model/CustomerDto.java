package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @ApiModelProperty(value = "This is the first name", required = true)
    private String firstName;

    @ApiModelProperty(required = true )
    private String lastName;

    @JsonProperty("customer_url")
    private String customerUrl;
}
