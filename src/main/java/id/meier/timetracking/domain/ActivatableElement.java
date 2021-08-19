package id.meier.timetracking.domain;

public interface ActivatableElement extends PersistableElement {
    Boolean getActive() ;
    void setActive(Boolean active);
}
