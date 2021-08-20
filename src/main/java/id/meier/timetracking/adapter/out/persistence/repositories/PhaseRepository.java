package id.meier.timetracking.adapter.out.persistence.repositories;

import id.meier.timetracking.adapter.out.persistence.entities.PhaseEntity;
import id.meier.timetracking.adapter.out.persistence.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhaseRepository extends JpaRepository<PhaseEntity, Long> {
    List<PhaseEntity> findByNameStartsWithIgnoreCase(String name);
    List<PhaseEntity> findByTasksIn(List<TaskEntity> tasks);
}
