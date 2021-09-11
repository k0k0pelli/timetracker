package id.meier.timetracking.web.projects;

import id.meier.timetracking.domain.DescribedElement;

public interface ElementEditorChangeListener<T extends DescribedElement> {
         void onChange(ChangeAction changeAction, T element);
    enum ChangeAction {
        SAVE, CANCEL, DELETE
    }
}