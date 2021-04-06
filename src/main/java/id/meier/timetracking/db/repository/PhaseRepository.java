package id.meier.timetracking.db.repository;

import id.meier.timetracking.db.entity.PhaseEntity;
import id.meier.timetracking.db.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhaseRepository extends JpaRepository<PhaseEntity, Long> {
    List<PhaseEntity> findByNameStartsWithIgnoreCase(String name);
    List<PhaseEntity> findByTasksIn(List<TaskEntity> tasks);
}
