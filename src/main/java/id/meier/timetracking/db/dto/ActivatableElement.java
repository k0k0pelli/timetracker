package id.meier.timetracking.db.dto;

public interface ActivatableElement extends PersistableElement {
    Boolean getActive() ;
    void setActive(Boolean active);
}
