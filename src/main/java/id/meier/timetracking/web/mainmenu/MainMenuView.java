package id.meier.timetracking.web.mainmenu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;

import id.meier.timetracking.web.assignments.AssignmentManagementView;
import id.meier.timetracking.web.projects.ProjectsManagementView;
import id.meier.timetracking.web.reporting.ReportingManagementView;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

@Route("timetracker2")
@Order(11)
class MainMenuView extends HorizontalLayout implements BeforeEnterListener, AfterNavigationListener, BeforeLeaveListener {
	private final String WIDTH = "15em";
	private Component displayedComponent = null;
	private final Button selectedBtn=null;

	private final AssignmentManagementView manageAssignments;
	private final ProjectsManagementView manageProjects;
	private final ReportingManagementView reporting;

	private final Map<Tab, Component> tabResolver;

	
	public MainMenuView(AssignmentManagementView manageAssignments, ProjectsManagementView manageProjects,
                        ReportingManagementView reporting) {
		UI.getCurrent().addBeforeEnterListener(this);
		UI.getCurrent().addBeforeLeaveListener(this);
		UI.getCurrent().addAfterNavigationListener(this);
		this.manageAssignments = manageAssignments;
		this.manageProjects = manageProjects;
		this.reporting = reporting;
		tabResolver = new HashMap<>();

		Tabs menu = createTabsMenu();
		menu.getStyle().set("background", "cccccc");
		

		menu.setWidth("15em");
		menu.setHeight("100%");

		this.add(menu);
		switchPanel(this.manageAssignments);
	}

	private Tabs createTabsMenu() {
		Tabs tabs = new Tabs();
		Tab assignmentsTab = new Tab(getTranslation("time.tracking.menu.assignments"));
		tabResolver.put(assignmentsTab, this.manageAssignments);
		Tab tab2 = new Tab(getTranslation("time.tracking.menu.projects"));
		tabResolver.put(tab2, this.manageProjects);
		Tab tab4 = new Tab(getTranslation("time.tracking.menu.reports"));
		tabResolver.put(tab4, this.reporting);
		tabs.add(assignmentsTab, tab2, tab4);
		tabs.setOrientation(Tabs.Orientation.VERTICAL);
		tabs.addSelectedChangeListener(e -> switchPanel(tabResolver.get(e.getSelectedTab())));
		return tabs;
	}

	private void switchPanel(Component newPanel) {
		if (this.displayedComponent != null) {
			this.remove(this.displayedComponent);
			displayedComponent.setVisible(false);
		}

		this.add(newPanel);
		this.displayedComponent = newPanel;
		displayedComponent.setVisible(true);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		System.out.println();
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		System.out.println();
	}

	@Override
	public void beforeLeave(BeforeLeaveEvent event) {
		System.out.println();
	}
}
