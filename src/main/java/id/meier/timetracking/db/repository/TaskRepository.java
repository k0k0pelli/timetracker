package id.meier.timetracking.db.repository;

import id.meier.timetracking.db.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByNameStartsWithIgnoreCase(String name);
}
