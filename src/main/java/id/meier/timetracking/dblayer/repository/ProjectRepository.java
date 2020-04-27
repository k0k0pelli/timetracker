package id.meier.timetracking.dblayer.repository;

import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByNameStartsWithIgnoreCase(String name);

    List<Project> findByPhasesIn(List<Phase> phases);

}
