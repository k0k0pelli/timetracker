package id.meier.timetracking.model;

public interface ActivatableElement extends PersistableElement {
    Boolean getActive() ;
    void setActive(Boolean active);
}
