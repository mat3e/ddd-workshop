package io.github.mat3e.tasker.task.dto;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class TaskDto {
    public static TaskDto of(String description, ZonedDateTime deadline) {
        return new TaskDto(0, description, false, deadline, null);
    }

    private final int id;
    @NotNull
    private final String description;
    private final boolean done;
    private final ZonedDateTime deadline;
    private final String additionalComment;

    public TaskDto(
            int id,
            String description,
            boolean done,
            ZonedDateTime deadline,
            String additionalComment
    ) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.additionalComment = additionalComment;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public TaskDto withId(int id) {
        return new TaskDto(
                id,
                this.description,
                this.done,
                this.deadline,
                this.additionalComment
        );
    }
}
