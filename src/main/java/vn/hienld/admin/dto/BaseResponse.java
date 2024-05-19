package vn.hienld.admin.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"err_code", "message"})
@Data
public class BaseResponse {

    @JsonProperty("err_code")
    private String err_code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Object data;

    @JsonProperty("total")
    private Long total;

    public BaseResponse() {
        this.err_code = "0";
        this.message = "SUCCESS";
    }

    public BaseResponse(String err_code, String message) {
        this.err_code = err_code;
        this.message = message;
    }
}
