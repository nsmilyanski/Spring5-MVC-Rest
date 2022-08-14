package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDto {

    @ApiModelProperty(value = "This is the name", required = true)
    private String name;

    @ApiModelProperty(value = "This is the vendor url", required = true)
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
