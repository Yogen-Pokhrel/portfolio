package com.portfolio.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaginationDto {

    @Min(value = 1, message = "Page number must be at least 1")
    private int page = 1;

    @Positive(message = "Size must be a positive number")
    private int size = 10;
}
