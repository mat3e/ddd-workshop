package io.github.mat3e.tasker.task;

import io.github.mat3e.tasker.task.dto.TaskDto;
import org.springframework.data.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface TaskQueryRepository extends Repository<Task, Integer> {
    int count();

    default boolean areUndoneTasksWithProjectId(int projectId) {
        return !findAllByDoneIsFalseAndProject_Id(projectId).isEmpty();
    }

    List<Task> findAllByDoneIsFalseAndProject_Id(int id);

    default <T> List<T> findAll(Class<T> type) {
        return new ArrayList<>(findAllBy(type));
    }

    <T> Set<T> findAllBy(Class<T> type);

    TaskDto getSingleById(Integer id);
}
