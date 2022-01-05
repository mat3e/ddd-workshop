package io.github.mat3e.tasker.project;

import io.github.mat3e.tasker.project.query.SimpleProjectQueryDto;
import io.github.mat3e.tasker.task.TaskDto;
import io.github.mat3e.tasker.task.TaskFacade;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class ProjectFacade {
    private final ProjectRepository projectRepository;
    private final TaskFacade taskFacade;

    ProjectFacade(final ProjectRepository projectRepository, TaskFacade taskFacade) {
        this.projectRepository = projectRepository;
        this.taskFacade = taskFacade;
    }

    List<TaskDto> createTasks(int projectId, ZonedDateTime projectDeadline) {
        if (taskFacade.areUndoneTasksWithProjectId(projectId)) {
            throw new IllegalStateException("There are still some undone tasks from a previous project instance!");
        }
        return get(projectId).map(project -> {
            List<TaskDto> tasks = project.getSteps().stream()
                    .map(step -> TaskDto.builder()
                            .withDescription(step.getDescription())
                            .withDeadline(projectDeadline.plusDays(step.getDaysToProjectDeadline()))
                            .build()
                    ).collect(toList());
            return taskFacade.saveAll(tasks, new SimpleProjectQueryDto(projectId, project.getName()));
        }).orElseThrow(() -> new IllegalArgumentException("No project found with id: " + projectId));
    }

    List<Project> list() {
        return projectRepository.findAll();
    }

    Optional<Project> get(int id) {
        return projectRepository.findById(id);
    }
}
