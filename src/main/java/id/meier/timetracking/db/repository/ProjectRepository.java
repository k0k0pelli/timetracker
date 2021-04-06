package id.meier.timetracking.db.repository;

import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findByNameStartsWithIgnoreCase(String name);

    List<ProjectEntity> findByPhasesIn(List<PhaseEntity> phases);

}
