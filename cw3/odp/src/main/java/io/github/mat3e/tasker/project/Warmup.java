package io.github.mat3e.tasker.project;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component("projectWarmup")
class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    private final ProjectRepository projectRepository;

    Warmup(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (projectRepository.count() == 0) {
            var project = new Project(
                    "Example project",
                    Set.of(
                            new ProjectStep("First step", -3),
                            new ProjectStep("Step 2", 1),
                            new ProjectStep("Stepbrother", 0)
                    )
            );
            projectRepository.save(project);
        }
    }
}
