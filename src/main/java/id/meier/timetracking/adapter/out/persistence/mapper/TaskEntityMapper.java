package id.meier.timetracking.adapter.out.persistence.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import id.meier.timetracking.adapter.out.persistence.entities.TaskEntity;
import id.meier.timetracking.domain.Task;

public class TaskEntityMapper {
    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public TaskEntity toEntityObject(Task domainTask) {
        if (domainTask == null) return null;
        return mapper.map(domainTask, TaskEntity.class);
    }

    public Task toDomainObject(TaskEntity entityTask) {
        if (entityTask == null) return null;
        return mapper.map(entityTask, Task.class);
    }

    public TaskEntity toEntityObject(Task sourceTask, TaskEntity targetTask) {
        if (sourceTask == null || targetTask == null) return null;
        mapper.map(sourceTask, targetTask);
        return targetTask;
    }

    public Task toDomainObject(TaskEntity sourceTask, Task targetTask) {
        if (sourceTask == null || targetTask == null) return null;
        mapper.map(sourceTask, targetTask);
        return targetTask;
    }
}
