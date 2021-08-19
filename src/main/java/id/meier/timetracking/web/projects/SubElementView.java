package id.meier.timetracking.web.projects;

import id.meier.timetracking.domain.DescribedElement;

abstract class SubElementView<T extends DescribedElement, PARENT extends DescribedElement>  extends ElementView<T> implements SubElementEditor.BaseEditorChangeHandler<T> {
    PARENT parent;
    SubElementView(boolean horizontalLayout,  Class<T> clazz, ElementEditor<T> editor) {
        super(horizontalLayout, clazz, editor);
    }

    void setParent(PARENT parent) {
        this.parent = parent;
    }


}
