package id.meier.timetracking.ui.projects;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import id.meier.timetracking.businesslayer.CommandsCollector;
import id.meier.timetracking.model.DescribedElement;

import java.util.ArrayList;
import java.util.List;


public abstract class ElementEditor<T extends DescribedElement>  extends VerticalLayout implements ElementViewChangeListener<T> {
	private TextField name; // dynamic binding, do not remove
	private TextArea description; // dynamic binding, do not remove
	private Checkbox active; // dynamic binding, do not remove
	private final FormLayout panel;
    private final HorizontalLayout actionPanel;
    private final HorizontalLayout emptyPanel;
	private final Binder<T> binder;
	final CommandsCollector commandsCollector;
	private T element;
	private final Class<T> clazz;
	final List<ElementEditorChangeListener<T>> changeListener;
	
	ElementEditor(CommandsCollector commandsCollector, Class<T> clazz) {
		changeListener = new ArrayList<>();

		 binder = new Binder<>(clazz);
		 this.clazz = clazz;
		 panel = getEditorPanel();
		 actionPanel = createActionPanel();
         emptyPanel = createEmptyPanel();
		 binder.bindInstanceFields(this);
		 this.getStyle().set("border", "1px solid LightGray");
		 this.commandsCollector = commandsCollector;
		 this.add(panel, actionPanel, emptyPanel);
        setEditorVisible(false);

	}
	
	void editElement(T element) {

	    if (element == null) {
            setEditorVisible(false);
			return;
		}
        setEditorVisible(true);
		binder.setBean(element);
		this.element = element;

	}

	private void setEditorVisible(boolean editorVisible) {
        if (!editorVisible) {
            emptyPanel.setVisible(true);
            panel.setVisible(false);
            actionPanel.setVisible(false);
        } else {
            emptyPanel.setVisible(false);
            panel.setVisible(true);
            actionPanel.setVisible(true);
        }
    }

	public void viewElementSelected(T element) {
		editElement(element);
	}

    private HorizontalLayout createEmptyPanel() {
	    HorizontalLayout h = new HorizontalLayout();
        h.getStyle().set("border", "0px");
        h.setMinWidth("100%");

        return h;
    }

    private FormLayout getEditorPanel() {
		FormLayout panel = new FormLayout();
		name = new TextField(getNameLabel());
		description = new TextArea(getDescriptionLabel());
		active = new Checkbox(getActiveLabel());
		panel.add(name, description, active);
		panel.setResponsiveSteps(
    			new ResponsiveStep("20em", 1), 
    			new ResponsiveStep("20em", 1));
		return panel;
	}
	
	void addDescribedElementModifiedListener(ElementEditorChangeListener<T> listener) {
		this.changeListener.add(listener);
	}

	void fireElementModified(ElementEditorChangeListener.ChangeAction action) {
		for (ElementEditorChangeListener<T> l : changeListener) {
			l.onChange(action);
		}
	}

	private HorizontalLayout createActionPanel() {
		HorizontalLayout phaseActions = new HorizontalLayout();
		Button save = new Button(getSaveLabel(), VaadinIcon.CHECK.create(),
			e -> {
				saveEditedElement(element);
				editElement(null);
				fireElementModified(ElementEditorChangeListener.ChangeAction.SAVE);
			}
		);
		Button delete = new Button(getDeleteLabel(), VaadinIcon.TRASH.create(),
			e -> {
				deleteEditedElement(element);
				editElement(null);
				fireElementModified(ElementEditorChangeListener.ChangeAction.DELETE);
			}
		);
		Button cancel = new Button(getCancelLabel(), e -> { editElement(null); fireElementModified(ElementEditorChangeListener.ChangeAction.CANCEL); });
		phaseActions.add(save, delete, cancel);			

		return phaseActions;
	}

	protected abstract void saveEditedElement(T element);
	protected abstract void deleteEditedElement(T element);

	protected abstract String getNameLabel();
	protected abstract String getDescriptionLabel();
	protected abstract String getActiveLabel();
	protected abstract String getDeleteLabel();
	protected abstract String getCancelLabel();
	protected abstract String getSaveLabel();
	

	
}
