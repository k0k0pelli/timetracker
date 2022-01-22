package id.meier.timetracking.application.port.in.structuremanagment;

import id.meier.timetracking.domain.PersistableElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StructureModificationCollector {
    private List<PersistableElement> saveCommands;
    private List<PersistableElement> removeCommands;
    public StructureModificationCollector() {
        saveCommands = new ArrayList<>();
        removeCommands = new ArrayList<>();
    }

    public <T extends PersistableElement> void addElementForSaveIfNotContained(T element) {
        if (!getSavedElements(element.getClass()).contains(element))  {
            saveCommands.add(element);
        }
    }

    public <T extends PersistableElement> void addElementForRemoveIfNotContained(T element) {
        if (!getRemovedElements(element.getClass()).contains(element)) {
            removeCommands.add(element);
        }
    }

    public void clear() {
        saveCommands.clear();
        removeCommands.clear();
    }

    public <T extends PersistableElement> List<T> getRemovedElements(Class<T> clazz) {
        return removeCommands
                .stream()
                .filter(c -> clazz.isAssignableFrom(c.getClass()))
                .map(c -> clazz.cast(c))
                .collect(Collectors.toList());
    }

    public <T extends PersistableElement> List<T> getSavedElements(Class<T> clazz) {
        return saveCommands
                .stream()
                .filter(c -> clazz.isAssignableFrom(c.getClass()))
                .map(c -> clazz.cast(c))
                .collect(Collectors.toList());
    }



}
