package id.meier.timetracking.adapter.out.persistence.repositories;

import id.meier.timetracking.adapter.out.persistence.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByNameStartsWithIgnoreCase(String name);
}
