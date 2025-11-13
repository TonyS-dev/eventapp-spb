package com.codeup.eventapp.infrastructure.web.response;

import org.springframework.data.domain.Page;

import com.codeup.eventapp.infrastructure.util.Trace;

public record AppResponse<T>(String status, T data, Meta meta) {

    private static final String SUCCESS = "success";

    public static <T> AppResponse<T> success(T data) {
        return new AppResponse<>(SUCCESS, data, null);
    }

    public static <T> AppResponse<T> success(T data, String msg) {
        return new AppResponse<>(SUCCESS, data, new Meta(msg, Trace.currentId(), "v1", null));
    }

    public static <T> AppResponse<T> withMeta(T data, Meta meta) {
        return new AppResponse<>(SUCCESS, data, meta);
    }

    public static <T> AppResponse<T> withPage(T data, Page<?> p) {
        Pagination pg = new Pagination(
                p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
        return new AppResponse<>(SUCCESS, data, new Meta(null, Trace.currentId(), "v1", pg));
    }

    public record Meta(String message, String traceId, String version, Pagination page) {}
    public record Pagination(int page, int size, long totalElements, int totalPages) {}
}
