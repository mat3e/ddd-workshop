package io.github.mat3e.exception;

public class BusinessException extends RuntimeException {
    public static BusinessException notFound(String entityName) {
        return new EntityNotFoundException(entityName);
    }

    public BusinessException(String message) {
        super(message);
    }
}
