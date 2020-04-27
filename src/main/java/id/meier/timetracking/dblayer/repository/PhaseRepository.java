package id.meier.timetracking.dblayer.repository;

import id.meier.timetracking.model.Phase;
import id.meier.timetracking.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
    List<Phase> findByNameStartsWithIgnoreCase(String name);
    List<Phase> findByTasksIn(List<Task> tasks);
}
