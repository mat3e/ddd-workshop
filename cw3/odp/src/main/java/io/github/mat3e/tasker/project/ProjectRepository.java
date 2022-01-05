package io.github.mat3e.tasker.project;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface ProjectRepository extends Repository<Project, Integer> {
    <S extends Project> S save(S entity);

    Optional<Project> findById(Integer id);

    List<Project> findAll();

    long count();
}
