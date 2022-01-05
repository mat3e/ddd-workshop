package io.github.mat3e.tasker.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "project_steps")
class ProjectStep {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    @NotNull
    private String description;
    private int daysToProjectDeadline;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @PersistenceConstructor
    protected ProjectStep() {
    }

    ProjectStep(@NotNull String description, int daysToProjectDeadline) {
        this.description = description;
        this.daysToProjectDeadline = daysToProjectDeadline;
    }

    ProjectStep withProject(Project project) {
        var result = new ProjectStep(description, daysToProjectDeadline);
        result.project = project;
        return result;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getDaysToProjectDeadline() {
        return daysToProjectDeadline;
    }

    public Project getProject() {
        return project;
    }
}
