package id.meier.timetracking.ui.projects;

import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.model.DescribedElement;

abstract class SubElementView<T extends DescribedElement, PARENT extends DescribedElement>  extends ElementView<T> implements SubElementEditor.BaseEditorChangeHandler<T> {
    PARENT parent;
    SubElementView(boolean horizontalLayout, CommandsCollector commandsCollector, Class<T> clazz, ElementEditor<T> editor) {
        super(horizontalLayout, commandsCollector, clazz, editor);
    }

    void setParent(PARENT parent) {
        this.parent = parent;
    }


}
