package com.portfolio.cms.modules.country.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class CountryResponseDto {
    @Schema
    private Integer id;
    @Schema
    private String isoCode;
    @Schema
    private String name;
    @Schema
    private String displayName;
    @Schema
    private String phoneCode;
    @Schema
    private String flag;
}
