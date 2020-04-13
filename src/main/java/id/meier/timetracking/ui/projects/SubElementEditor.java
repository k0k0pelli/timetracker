package id.meier.timetracking.ui.projects;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.model.DescribedElement;

public abstract class SubElementEditor<T extends DescribedElement>  extends ElementEditor<T> {
    private BaseEditorChangeHandler<T> baseEditorChangeHandler;

    SubElementEditor(CommandsCollector commandsCollector, Class<T> clazz) {
        super(commandsCollector, clazz);
    }

    @Override
    protected void saveEditedElement(T element) {
        commandsCollector.save(element);
        if (changeListener != null && element.getId() == null) {
            baseEditorChangeHandler.addElement(element);
        }
    }

    @Override
    protected void deleteEditedElement(T element) {
        commandsCollector.delete(element);
        this.baseEditorChangeHandler.removeElement(element);
    }

    void setBaseEditorChangeHandler(BaseEditorChangeHandler<T> handler) {
        this.baseEditorChangeHandler = handler;
    }

    public interface BaseEditorChangeHandler<T extends DescribedElement> {
        void addElement(T element);
        void removeElement(T element);
    }
}
