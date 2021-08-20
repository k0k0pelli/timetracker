package id.meier.timetracking.adapter.out.persistence;

import id.meier.timetracking.adapter.out.persistence.entities.PhaseEntity;
import id.meier.timetracking.adapter.out.persistence.entities.ProjectEntity;
import id.meier.timetracking.adapter.out.persistence.entities.TaskEntity;
import id.meier.timetracking.adapter.out.persistence.mapper.PhaseEntityMapper;
import id.meier.timetracking.adapter.out.persistence.mapper.ProjectEntityMapper;
import id.meier.timetracking.adapter.out.persistence.mapper.TaskEntityMapper;
import id.meier.timetracking.adapter.out.persistence.repositories.PhaseRepository;
import id.meier.timetracking.adapter.out.persistence.repositories.ProjectRepository;
import id.meier.timetracking.adapter.out.persistence.repositories.TaskRepository;
import id.meier.timetracking.application.port.out.commands.*;
import id.meier.timetracking.application.port.out.ManageProjectStructurePort;
import id.meier.timetracking.application.port.out.SelectionProjectStructurePort;
import id.meier.timetracking.domain.Phase;
import id.meier.timetracking.domain.Project;
import id.meier.timetracking.domain.Task;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectStructurePersistenceAdapter implements ManageProjectStructurePort, SelectionProjectStructurePort {

    private ProjectEntityMapper projectMapper = new ProjectEntityMapper();
    private PhaseEntityMapper phaseMapper = new PhaseEntityMapper();
    private TaskEntityMapper taskMapper = new TaskEntityMapper();

    private ProjectRepository projectRepository;
    private PhaseRepository phaseRepository;
    private TaskRepository taskRepository;

    public ProjectStructurePersistenceAdapter(ProjectRepository projectRepository, PhaseRepository phaseRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.phaseRepository = phaseRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Project saveProject(SaveProjectEntityCommand command) {
        ProjectEntity project = projectMapper.toEntityObject(command.getProject());
        project = projectRepository.save(project);
        return projectMapper.toDomainObject(project, command.getProject());
    }

    @Override
    public Phase savePhase(SavePhaseEntityCommand command) {
        PhaseEntity phase = phaseMapper.toEntityObject(command.getPhase());
        phase = phaseRepository.save(phase);
        return phaseMapper.toDomainObject(phase, command.getPhase());
    }

    @Override
    public Task saveTask(SaveTaskEntityCommand command) {
        TaskEntity task = taskMapper.toEntityObject(command.getTask());
        task = taskRepository.save(task);
        return taskMapper.toDomainObject(task, command.getTask());
    }



    @Override
    public void removeProject(RemoveProjectEntityCommand command) {
        ProjectEntity project = projectMapper.toEntityObject(command.getProject());
        projectRepository.delete(project);
    }

    @Override
    public void removePhase(RemovePhaseEntityCommand command) {
        PhaseEntity phase = phaseMapper.toEntityObject(command.getPhase());
        phaseRepository.delete(phase);
    }

    @Override
    public void removeTask(RemoveTaskEntityCommand command) {
        TaskEntity task = taskMapper.toEntityObject(command.getTask());
        taskRepository.delete(task);
    }

    @Override
    public Project selectProject(SelectProjectEntityCommand command) {
        ProjectEntity project = null;
        if (command.getId().isPresent()) {
            project = projectRepository.findById(command.getId().get()).orElse(null);
        }
        if (project == null) {
            List<ProjectEntity> list = projectRepository.findAll();
            project = list.stream().findFirst().orElse(null);
        }
        Project returnValue = projectMapper.toDomainObject(project);
        return returnValue;
    }

    @Override
    public List<Project> selectAllProjects() {
        List<ProjectEntity> list = projectRepository.findAll();
        return list.stream().map(p -> projectMapper.toDomainObject(p)).collect(Collectors.toList());
    }

    @Override
    public List<Phase> selectPhases(SelectPhaseEntityCommand command) {
        PhaseEntity phase = null;
        List<Phase> returnValue = null;
        if (command.getId().isPresent()) {
            phase = phaseRepository.findById(command.getId().get()).orElse(null);
            returnValue = Arrays.asList(phaseMapper.toDomainObject(phase));
        }
        if (phase == null) {
            List<PhaseEntity> list = phaseRepository.findAll();
            returnValue = list.stream().map(p -> phaseMapper.toDomainObject(p)).collect(Collectors.toList());
        }

        return returnValue;
    }

    @Override
    public List<Task> selectTask(SelectTaskEntityCommand command) {
        TaskEntity task = null;
        List<Task> returnValue = null;
        if (command.getId().isPresent()) {
            task = taskRepository.findById(command.getId().get()).orElse(null);
            returnValue = Arrays.asList(taskMapper.toDomainObject(task));
        }
        if (task == null) {
            List<TaskEntity> list = taskRepository.findAll();
            returnValue = list.stream().map(t -> taskMapper.toDomainObject(t)).collect(Collectors.toList());
        }

        return returnValue;
    }
}
