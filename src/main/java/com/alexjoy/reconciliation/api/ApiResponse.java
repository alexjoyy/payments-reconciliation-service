package com.alexjoy.reconciliation.api;

public record ApiResponse<T>(
    String timestamp,
    int status,
    String message,
    String path,
    T data
) {
}
