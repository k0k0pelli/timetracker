package id.meier.timetracking.web.projects;

import id.meier.timetracking.adapter.in.web.ManageProjectStructureController;
import id.meier.timetracking.domain.DescribedElement;

public abstract class SubElementEditor<T extends DescribedElement>  extends ElementEditor<T> {
    private BaseEditorChangeHandler<T> baseEditorChangeHandler;

    SubElementEditor(Class<T> clazz) {
        super(clazz);
    }

    @Override
    protected void saveEditedElement(T element) {
        if (changeListener != null && element.getId() == null) {
            baseEditorChangeHandler.addElement(element);
        }
        saveElement(element);
    }

    protected abstract void saveElement(T element);

    @Override
    protected void deleteEditedElement(T element) {
        deleteElement(element);
        this.baseEditorChangeHandler.removeElement(element);
    }

    protected abstract void deleteElement(T element);

    void setBaseEditorChangeHandler(BaseEditorChangeHandler<T> handler) {
        this.baseEditorChangeHandler = handler;
    }

    public interface BaseEditorChangeHandler<T extends DescribedElement> {
        void addElement(T element);
        void removeElement(T element);
    }
}
