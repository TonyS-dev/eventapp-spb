package com.codeup.eventapp.web.response;

import org.springframework.data.domain.Page;

public record ApiResponse<T>(String status, T data, Meta meta) {

    public static <T> ApiResponse<T> success(T data, String msg) {
        return new ApiResponse<>("success", data, new Meta(msg, null));
    }

    public static <T> ApiResponse<T> withPage(T data, Page<?> p) {
        Pagination pg = new Pagination(
                p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
        return new ApiResponse<>("success", data, new Meta(null, pg));
    }

    public record Meta(String message, Pagination page) {}
    public record Pagination(int page, int size, long totalElements, int totalPages) {}
}