package epam.springboot.advanced.demo.models;

public record SimpleErrorResponse(
        String errorMessage,
        String errorCode
) {}
