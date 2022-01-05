package io.github.mat3e.tasker.project;

import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "projects")
class Project {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    @Valid
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.EAGER)
    private final Set<ProjectStep> steps = new HashSet<>();

    @PersistenceConstructor
    protected Project() {
    }

    Project(String name, Set<ProjectStep> steps) {
        this.name = name;
        steps.forEach(step -> this.steps.add(step.withProject(this)));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<ProjectStep> getSteps() {
        return steps;
    }
}
