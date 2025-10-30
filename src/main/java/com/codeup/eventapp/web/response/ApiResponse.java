package com.codeup.eventapp.web.response;

import org.springframework.data.domain.Page;

import com.codeup.eventapp.util.Trace;

public record ApiResponse<T>(String status, T data, Meta meta) {

    private static final String SUCCESS = "success";

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS, data, null);
    }

    public static <T> ApiResponse<T> success(T data, String msg) {
        return new ApiResponse<>(SUCCESS, data, new Meta(msg, Trace.currentId(), "v1", null));
    }

    public static <T> ApiResponse<T> withMeta(T data, Meta meta) {
        return new ApiResponse<>(SUCCESS, data, meta);
    }

    public static <T> ApiResponse<T> withPage(T data, Page<?> p) {
        Pagination pg = new Pagination(
                p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
        return new ApiResponse<>(SUCCESS, data, new Meta(null, Trace.currentId(), "v1", pg));
    }

    public record Meta(String message, String traceId, String version, Pagination page) {}
    public record Pagination(int page, int size, long totalElements, int totalPages) {}
}