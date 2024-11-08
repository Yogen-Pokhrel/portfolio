package com.portfolio.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class ApiResponse<T> {
    private final T data;
    private final String message;
    private final boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseMeta meta;

    ApiResponse(T data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    ApiResponse(T data, String message, boolean success, ResponseMeta meta) {
        this.data = data;
        this.message = message;
        this.success = success;
        this.meta = meta;
    }

    ApiResponse(T data, String message, boolean success, Page<?> page) {
        this.data = data;
        this.message = message;
        this.success = success;
        this.meta = createMetaFromPage(page);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message, true);
    }

    public static <T> ApiResponse<T> success(T data, String message, ResponseMeta meta) {
        return new ApiResponse<>(data, message, true, meta);
    }

    public static <T> ApiResponse<T> success(T data, String message, Page<?> page) {
        return new ApiResponse<>(data, message, true, page);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message, false);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(data, message, false);
    }

    public ResponseMeta createMetaFromPage(Page<?> page) {
        ResponseMeta meta = new ResponseMeta();
        meta.setPage(page.getNumber() + 1);
        meta.setPerPage(page.getSize());
        meta.setTotal(page.getTotalElements());
        meta.setPageCount(page.getTotalPages());
        return meta;
    }
}
