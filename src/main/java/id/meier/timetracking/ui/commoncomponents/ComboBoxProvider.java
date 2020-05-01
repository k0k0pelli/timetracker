package id.meier.timetracking.ui.commoncomponents;

import id.meier.timetracking.model.NamedElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComboBoxProvider<T extends NamedElement> {
	private final Class<T> containerClass;
	private final List<ComboBoxProvider.NamedObjectCapsule> elements;
	private final ElementRetriever<T> elementRetriever;
	private final List<ComboBoxProvider.NamedObjectCapsule> addedElements;

	public ComboBoxProvider(Class<T> containerClass, ElementRetriever<T> elementRetriever) {
		this.containerClass = containerClass;
		this.elements = new ArrayList<>();
		this.addedElements = new ArrayList<>();
		this.elementRetriever = elementRetriever;
		boolean initialized = false;
	}

	public ComboBoxProviderResult<T> checkAddIfNotFoundAndReturnAllElements(String name) throws InstantiationException, IllegalAccessException {
		populateElementsList();
		T newElement = null;
		if (name != null) {
			NamedObjectCapsule noc = new NamedObjectCapsule(name);
			if (!elements.contains(noc)) {
				newElement = noc.getEncapsulatedObject();
				elements.add(noc);
				addedElements.add(noc);
			}
		}
		return new ComboBoxProviderResult<>(newElement, getAllElements());
	}

	public List<T> getAllElements() {
		populateElementsList();
		return elements
				.stream()
				.map(e -> containerClass.cast(e.getEncapsulatedObject()))
				.filter(e -> e.getActive() != null && e.getActive())
				.collect(Collectors.toList());
	}

	public void clear() {
		this.addedElements.clear();
	}

	private void populateElementsList() {
		List<T> originalElements;
		if (elementRetriever != null) {
			originalElements = elementRetriever.getElements();
		} else {
			originalElements = new ArrayList<>();
		}

		elements.clear();

		List<ComboBoxProvider.NamedObjectCapsule> list = originalElements.stream()
				.map(NamedObjectCapsule::new)
				.collect(Collectors.toList());
		elements.addAll(list);
		elements.addAll(this.addedElements);
	}

	private class NamedObjectCapsule implements NamedElement {
		private final T encapsulatedObject;
			
		NamedObjectCapsule(String name) throws InstantiationException, IllegalAccessException {
			encapsulatedObject = containerClass.newInstance();
			this.setName(name);
		}
		
		NamedObjectCapsule(T encapsulatedObject) {
			this.encapsulatedObject = encapsulatedObject;
		}
		
		@Override
		public String getName() {			
			return encapsulatedObject.getName();
		}

		@Override
		public void setName(String name) {
			encapsulatedObject.setName(name);
		}
		
		T getEncapsulatedObject() {
			return encapsulatedObject;
		}
		
		public boolean equals(Object o) {
			boolean result = false;
			if (o instanceof NamedElement) {
				NamedElement e = (NamedElement)o;
				if (e.getName().contentEquals(this.getName())) result = true;
			}
			return result;
		}
		
		public int hashCode() {
			return this.getName().hashCode();
		}

		@Override
		public Long getId() {
			return encapsulatedObject.getId();
		}

		@Override
		public void setId(Long id) {
			encapsulatedObject.setId(id);
		}


		@Override
		public Boolean getActive() {
			return encapsulatedObject.getActive();
		}

		@Override
		public void setActive(Boolean active) {
			encapsulatedObject.setActive(active);
		}
	}


}

