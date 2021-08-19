package id.meier.timetracking.adapter.out.persistence.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import id.meier.timetracking.adapter.out.persistence.entities.AssignmentRefHitParadeProjectPhaseTaskEntity;

import id.meier.timetracking.domain.AssignmentRefHitParadeProjectPhaseTask;

public class AssignmentRefHitParadeProjectPhaseTaskEntityMapper {
    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public AssignmentRefHitParadeProjectPhaseTaskEntity toEntityObject(AssignmentRefHitParadeProjectPhaseTask domainProject) {
        if (domainProject == null) return null;
        return mapper.map(domainProject, AssignmentRefHitParadeProjectPhaseTaskEntity.class);
    }

    public AssignmentRefHitParadeProjectPhaseTask toDomainObject(AssignmentRefHitParadeProjectPhaseTaskEntity entityProject) {
        if (entityProject == null) return null;
        return mapper.map(entityProject, AssignmentRefHitParadeProjectPhaseTask.class);
    }
}
