package id.meier.timetracking.ui.projects;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.model.DescribedElement;
import id.meier.timetracking.model.NamedElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public abstract class ElementView<T extends DescribedElement>  extends VerticalLayout implements ElementEditorChangeListener<T> {

	private Grid<T> subElementList;
	protected final CommandsCollector commandsCollector;
	protected T editedElement;
	private final Class<T> clazz;
	private final ElementEditor<T> editor;
	private final Button newElementButton;
	final List<T> removedElements;
	final List<T> addedElements;
	List<T> allElements;
	private final List<ElementViewChangeListener<T>> changeListeners;


	ElementView(boolean horizontalLayout, CommandsCollector commandsCollector, Class<T> clazz, ElementEditor<T> editor) {
		 removedElements = new ArrayList<>();
		 addedElements = new ArrayList<>();
		 allElements = new ArrayList<>();
		 changeListeners = new ArrayList<>();
		 this.commandsCollector = commandsCollector;
		 this.clazz = clazz;
		 this.addListener(editor);
		 this.editor = editor;
		 this.subElementList = createSubElementGrid();

		 this.newElementButton = new Button(getAddNewElementLabel(), VaadinIcon.PLUS.create(), 
				 								e -> fireSelectedElement(createNewElement())
				 							);
		 subElementList.setWidthFull();
		 editor.setWidthFull();
		 Component c = createContainer(horizontalLayout, subElementList, editor);
         this.add(newElementButton, c);
         this.setMargin(false);
         this.setPadding(false);
	}

	private Component createContainer(boolean horizontalLayout, Component... components) {
		Component c;
	    if (! horizontalLayout) {
			VerticalLayout vl = new VerticalLayout();
			vl.setMargin(false);
			vl.setPadding(false);
			vl.add(components);
	        c = vl;
		} else {
			HorizontalLayout hl = new HorizontalLayout();
			hl.add(components);
			hl.setWidthFull();
			hl.setMargin(false);
			hl.setPadding(false);
			c = hl;
		}
	    return c;
	}

	void setData(List<T> elements) {
		allElements = elements;
		addedElements.clear();
		removedElements.clear();
		setFilteredItems(elements);
	}

	void enableAddButton(boolean enable) {
		this.newElementButton.setEnabled(enable);
	}

	void addListener(ElementViewChangeListener<T> listener) {
		this.changeListeners.add(listener);
	}

	void setFilteredItems(List<T> elements) {
		 List<T> tempElements;
		 if (elements != null) {
             tempElements = elements.stream().filter(
                     p -> !this.removedElements.contains(p)
             ).collect(
                     Collectors.toList()
             );
         } else {
             tempElements = new ArrayList<>();
         }
		List<T> allElements = new ArrayList<>(tempElements);
		allElements.addAll(addedElements);
		subElementList.setItems(allElements);
		subElementList.select(null);
        fireSelectedElement(null);
	 }
	
	private Grid<T> createSubElementGrid() {
		subElementList = new Grid<>();
		addColumnToGrid(
				NamedElement::getName,
				getNameLabel()).setSortable(true);
		addColumnToGrid(
				DescribedElement::getDescription,
				getDescriptionLabel()).setSortable(true);
		
		subElementList.setHeight(getGridHeight());
		this.subElementList.addSelectionListener(e ->
				fireSelectedElement(e.getFirstSelectedItem().orElse(null))
		);
		 
		return subElementList;
	}

	private void fireSelectedElement(T element ) {
		editor.editElement(element);
		for (ElementViewChangeListener<T> l : this.changeListeners) {
			l.viewElementSelected(element);
		}
	}

	private T createNewElement() {
		T element = null;
		try {
			element = clazz.newInstance();
		} catch (Exception ignored) {
		}
		return element;
	}
	
	 private Column<T> addColumnToGrid(ValueProvider<T, ?> valueProvider, String headerText) {
	    	return subElementList.addColumn(valueProvider).setHeader(headerText);
	 }
	 
	 
	 
	 /**
	  * @return Returns the label of the Save button.
	  */
	 protected abstract String getSaveLabel();
	 
	 /**
	  * @return Returns the label of the name text field.
	  */
	 protected abstract String getNameLabel();
	 
	 /**
	  * @return Returns the label of the description text area.
	  */
	 protected abstract String getDescriptionLabel();

	 
	 /**
	  * @return Returns the label for the new element button.
	  */
	 protected abstract String getAddNewElementLabel();

	protected  String getGridHeight() {
		return "140px";
	}
}
