package id.meier.timetracking.adapter.out.persistence.repositories;

import id.meier.timetracking.adapter.out.persistence.entities.PhaseEntity;
import id.meier.timetracking.adapter.out.persistence.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findByNameStartsWithIgnoreCase(String name);

    List<ProjectEntity> findByPhasesIn(List<PhaseEntity> phases);

}
