package io.github.mat3e.tasker.task;

import io.github.mat3e.tasker.project.query.SimpleProjectQueryDto;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tasks")
class Task {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    @NotNull
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    @ManyToOne
    @JoinColumn(name = "source_id")
    private SimpleProjectQueryDto project;

    @PersistenceConstructor
    public Task() {
    }

    Task(@NotNull String description, ZonedDateTime deadline, SimpleProjectQueryDto project) {
        this.description = description;
        this.deadline = deadline;
        this.project = project;
    }

    TaskDto toDto() {
        return TaskDto.builder()
                .withId(id)
                .withDescription(description)
                .withDone(done)
                .withDeadline(deadline)
                .withAdditionalComment(additionalComment)
                .build();
    }

    int countChanges() {
        return changesCount;
    }

    void switchTo(boolean state) {
        if (state == done) {
            return;
        }
        toggle();
    }

    void toggle() {
        done = !done;
        ++changesCount;
    }

    void updateInfo(@NotNull String description, ZonedDateTime deadline, String additionalComment) {
        if (done && anyChangeRequired(description, deadline, additionalComment)) {
            throw new ChangingDoneTaskException();
        }
        this.description = requireNonNull(description);
        this.deadline = deadline;
        this.additionalComment = additionalComment;
    }

    private boolean anyChangeRequired(String description, ZonedDateTime deadline, String additionalComment) {
        return !Objects.equals(description, this.description)
                || !Objects.equals(deadline.toInstant(), this.deadline.toInstant())
                || !Objects.equals(additionalComment, this.additionalComment);
    }

    static class ChangingDoneTaskException extends IllegalStateException {
        ChangingDoneTaskException() {
            super("Cannot change the description of a completed task");
        }
    }
}
