package id.meier.timetracking.ui.projects;

import id.meier.timetracking.db.dto.DescribedElement;

public interface ElementEditorChangeListener<T extends DescribedElement> {
         void onChange(ChangeAction changeAction);
    enum ChangeAction {
        SAVE, CANCEL, DELETE
    }
}