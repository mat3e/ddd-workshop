package io.github.mat3e.tasker.task;

import io.github.mat3e.tasker.project.query.SimpleProjectQueryDto;
import io.github.mat3e.tasker.task.dto.TaskDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class TaskFacade {
    private final TaskFactory taskFactory;
    private final TaskRepository taskRepository;

    TaskFacade(final TaskFactory taskFactory, final TaskRepository taskRepository) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
    }

    public List<TaskDto> saveAll(Collection<TaskDto> tasks, SimpleProjectQueryDto project) {
        return taskRepository.saveAll(
                tasks.stream()
                        .map(dto -> taskFactory.createNew(dto, project))
                        .collect(toList())
        ).stream().map(Task::toDto)
                .collect(toList());
    }

    TaskDto save(TaskDto toSave) {
        return taskRepository.save(
                taskRepository.findById(toSave.getId()).map(existingTask -> {
                    existingTask.switchTo(toSave.isDone());
                    existingTask.updateInfo(toSave.getDescription(), toSave.getDeadline(), toSave.getAdditionalComment());
                    return existingTask;
                }).orElseGet(() -> taskFactory.createNew(toSave, null))
        ).toDto();
    }

    Optional<TaskDto> get(int id) {
        return taskRepository.findById(id).map(Task::toDto);
    }

    void delete(int id) {
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            if (e.getMessage() != null && e.getMessage().contains("id " + id)) {
                // no task with a given ID - no problem, already deleted
                return;
            }
            throw e;
        }
    }
}
