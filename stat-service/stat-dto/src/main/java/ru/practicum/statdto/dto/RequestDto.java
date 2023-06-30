package ru.practicum.statdto.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class RequestDto {

    @NotBlank(message = "Service identifier must be provided, example: ewm-main-service")
    private String app;

    @NotBlank(message = "URI must be provided, example: /events/1")
    private String uri;

    @NotBlank(message = "ip-address must be provided, example: 192.163.0.1")
    private String ip;

    @NotNull(message = "Date and time of request must be provided, example: 2022-09-06 11:00:23")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;

}
