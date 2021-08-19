package id.meier.timetracking.adapter.out.persistence.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import id.meier.timetracking.adapter.out.persistence.entities.ProjectEntity;
import id.meier.timetracking.domain.Project;

public class ProjectEntityMapper {
    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public ProjectEntity toEntityObject(Project domainProject) {
        if (domainProject == null) return null;
        return mapper.map(domainProject, ProjectEntity.class);
    }

    public Project toDomainObject(ProjectEntity entityProject) {
        if (entityProject == null) return null;
        return mapper.map(entityProject, Project.class);
    }

    public ProjectEntity toEntityObject(Project sourceProject, ProjectEntity targetProject) {
        if (sourceProject == null || targetProject == null) return null;
        mapper.map(sourceProject, targetProject);
        return targetProject;
    }

    public Project toDomainObject(ProjectEntity sourceProject, Project targetProject) {
        if (sourceProject == null || targetProject == null) return null;
        targetProject.getPhases().clear();
        mapper.map(sourceProject, targetProject);
        return targetProject;
    }
}
