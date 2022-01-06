package io.github.mat3e.tasker.project;

import java.time.ZonedDateTime;

class ProjectDeadlineDto {
    private ZonedDateTime deadline;

    ZonedDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }
}
