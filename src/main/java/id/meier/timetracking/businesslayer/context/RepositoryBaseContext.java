package id.meier.timetracking.businesslayer.context;

import id.meier.timetracking.businesslayer.Context;
import id.meier.timetracking.businesslayer.commands.Command;
import id.meier.timetracking.model.PersistableElement;

import java.util.*;
import java.util.stream.Collectors;

public class RepositoryBaseContext implements Context {
    private final Map<ContextKey<? extends Command, ? extends PersistableElement>, Integer> contextKeyOrderMap;

    RepositoryBaseContext() {
        contextKeyOrderMap = new HashMap<>();
    }

    <T extends Command, U extends PersistableElement> void register(Class<T> commandClass, Class<U> persistableElementClass, int order) {
        this.contextKeyOrderMap.put(new ContextKey<>(commandClass, persistableElementClass), order);
    }

    @Override
    public int getOrder(Command command, PersistableElement... elements) {
        int order = 0;
        List<Class<? extends PersistableElement>> classList = Arrays.stream(elements).map(PersistableElement::getClass).collect(Collectors.toList());
        Class<?>[] elementsArray = new Class<?>[elements.length];
        elementsArray = classList.toArray(elementsArray);
        @SuppressWarnings("unchecked")
        ContextKey key = new ContextKey(command.getClass(), elementsArray);
        if (contextKeyOrderMap.containsKey(key)) {
            order = contextKeyOrderMap.get(key);
        }
        return order;
    }

    private static class ContextKey<T extends Command, U extends PersistableElement> {
        private final Class<T> cmdClass;
        private final Class<U>[] persistableElementClasses;

        @SafeVarargs
        ContextKey(Class<T> cmdClazz, Class<U>... persistableElementClasses) {
            this.cmdClass = cmdClazz;
            this.persistableElementClasses = persistableElementClasses;
        }

        @Override
        public boolean equals(Object o) {
            boolean result = false;
            if (o instanceof ContextKey) {
                result = Objects.equals(this.cmdClass.getName(), ((ContextKey)o).cmdClass.getName());
                result = result && Objects.equals(getPersistableElementClassesNames(), ((ContextKey)o).getPersistableElementClassesNames());
            }

            return result;

        }

        @Override
        public int hashCode() {
            return Objects.hash(cmdClass.getName(), getPersistableElementClassesNames());
        }

        private String getPersistableElementClassesNames() {
            return Arrays.stream(persistableElementClasses).map(Class::getName).collect(Collectors.joining("/"));
        }
    }
}
