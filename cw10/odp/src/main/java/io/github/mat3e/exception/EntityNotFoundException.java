package io.github.mat3e.exception;

class EntityNotFoundException extends BusinessException {
    EntityNotFoundException(String entityName) {
        super(entityName + " not found");
    }
}
