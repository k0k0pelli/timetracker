package id.meier.timetracking.dblayer.repository;

import id.meier.timetracking.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByNameStartsWithIgnoreCase(String name);
}
