package id.meier.timetracking.web.projects;

import id.meier.timetracking.application.port.in.structuremanagment.StructureModificationCollector;
import id.meier.timetracking.domain.DescribedElement;

abstract class SubElementView<T extends DescribedElement, PARENT extends DescribedElement>  extends ElementView<T> implements SubElementEditor.BaseEditorChangeHandler<T> {
    PARENT parent;
    SubElementView(boolean horizontalLayout,  Class<T> clazz, ElementEditor<T> editor, StructureModificationCollector modificationCommandsCollector) {
        super(horizontalLayout, clazz, editor, modificationCommandsCollector);
    }

    void setParent(PARENT parent) {
        this.parent = parent;
    }

}
