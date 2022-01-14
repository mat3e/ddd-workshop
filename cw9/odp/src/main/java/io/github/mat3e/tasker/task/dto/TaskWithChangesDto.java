package io.github.mat3e.tasker.task.dto;

import java.time.ZonedDateTime;

public interface TaskWithChangesDto {
    int getId();

    String getDescription();

    boolean isDone();

    ZonedDateTime getDeadline();

    int getChangesCount();
}
