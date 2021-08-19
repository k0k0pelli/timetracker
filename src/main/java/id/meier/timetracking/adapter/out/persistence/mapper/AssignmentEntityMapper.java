package id.meier.timetracking.adapter.out.persistence.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import id.meier.timetracking.adapter.out.persistence.entities.AssignmentEntity;
import id.meier.timetracking.domain.Assignment;

public class AssignmentEntityMapper {
    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public AssignmentEntity toEntityObject(id.meier.timetracking.domain.Assignment domainAssignment) {
        if (domainAssignment == null) return null;
        return mapper.map(domainAssignment, AssignmentEntity.class);
    }

    public Assignment toDomainObject(AssignmentEntity entityProject) {
        if (entityProject == null) return null;
        return mapper.map(entityProject, id.meier.timetracking.domain.Assignment.class);
    }

    public AssignmentEntity toEntityObject(Assignment assignmentSource, AssignmentEntity assignmentTarget) {
        if (assignmentSource == null || assignmentTarget == null) return null;
        mapper.map(assignmentSource, assignmentTarget);
        return assignmentTarget;
    }

    public Assignment toDomainObject(AssignmentEntity sourceAssignment, Assignment targetAssignment) {
        if (sourceAssignment == null || targetAssignment == null) return null;
        mapper.map(sourceAssignment, targetAssignment);
        return targetAssignment;
    }
}
