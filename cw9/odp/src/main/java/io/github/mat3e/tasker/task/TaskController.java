package io.github.mat3e.tasker.task;

import io.github.mat3e.tasker.task.dto.TaskDto;
import io.github.mat3e.tasker.task.dto.TaskWithChangesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
class TaskController {
    private final TaskFacade taskFacade;
    private final TaskQueryRepository taskQueryRepository;

    TaskController(final TaskFacade taskFacade, final TaskQueryRepository taskQueryRepository) {
        this.taskFacade = taskFacade;
        this.taskQueryRepository = taskQueryRepository;
    }

    @GetMapping
    List<TaskDto> list() {
        return taskQueryRepository.findAll(TaskDto.class);
    }

    @GetMapping(params = "changes")
    List<TaskWithChangesDto> listWithChanges() {
        return taskQueryRepository.findAll(TaskWithChangesDto.class);
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskDto> get(@PathVariable int id) {
        return ResponseEntity.of(Optional.ofNullable(taskQueryRepository.getSingleById(id)));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable int id, @RequestBody TaskDto toUpdate) {
        if (id != toUpdate.getId() && toUpdate.getId() != 0) {
            return ResponseEntity.badRequest().body("Id in URL is different than in body: " + id + " and " + toUpdate.getId());
        }
        taskFacade.save(toUpdate.withId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<TaskDto> create(@RequestBody @Valid TaskDto toCreate) {
        TaskDto result = taskFacade.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<TaskDto> delete(@PathVariable int id) {
        taskFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
