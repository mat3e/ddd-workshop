package io.github.mat3e.tasker.task;

import io.github.mat3e.tasker.project.query.SimpleProjectQueryDto;
import org.springframework.stereotype.Service;

@Service
class TaskFactory {
    Task createNew(final TaskDto source, final SimpleProjectQueryDto project) {
        var result = new Task(source.getDescription(), source.getDeadline(), project);
        result.updateInfo(source.getDescription(), source.getDeadline(), source.getAdditionalComment());
        return result;
    }
}
