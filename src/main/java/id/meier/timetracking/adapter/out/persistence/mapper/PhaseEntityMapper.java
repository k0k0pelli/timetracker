package id.meier.timetracking.adapter.out.persistence.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import id.meier.timetracking.adapter.out.persistence.entities.PhaseEntity;
import id.meier.timetracking.domain.Phase;

public class PhaseEntityMapper {
    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public PhaseEntity toEntityObject(Phase domainPhase) {
        if (domainPhase == null) return null;
        return mapper.map(domainPhase, PhaseEntity.class);
    }

    public Phase toDomainObject(PhaseEntity entityPhase) {
        if (entityPhase == null) return null;
        return mapper.map(entityPhase, Phase.class);
    }

    public PhaseEntity toEntityObject(Phase sourcePhase, PhaseEntity targetPhase) {
        if (sourcePhase == null || targetPhase == null) return null;
        mapper.map(sourcePhase, targetPhase);
        return targetPhase;
    }

    public Phase toDomainObject(PhaseEntity sourcePhase, Phase targetPhase) {
        if (sourcePhase == null || targetPhase == null) return null;
        targetPhase.getTasks().clear();
        mapper.map(sourcePhase, targetPhase);
        return targetPhase;
    }
}
