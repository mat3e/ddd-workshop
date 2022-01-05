package io.github.mat3e.tasker.project;

import io.github.mat3e.tasker.task.TaskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
class ProjectController {
    private final ProjectFacade projectFacade;

    ProjectController(ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    @GetMapping
    List<Project> list() {
        return projectFacade.list();
    }

    @GetMapping("/{id}")
    ResponseEntity<Project> get(@PathVariable int id) {
        return ResponseEntity.of(projectFacade.get(id));
    }

    @PostMapping("/{id}/tasks")
    List<TaskDto> createTasks(@PathVariable int id, @RequestBody ProjectDeadlineDto deadlineDto) {
        return projectFacade.createTasks(id, deadlineDto.getDeadline());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleClientError(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleClientError(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }
}
