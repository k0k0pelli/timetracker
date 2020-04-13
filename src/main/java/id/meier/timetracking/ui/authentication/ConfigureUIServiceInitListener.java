package id.meier.timetracking.ui.authentication;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import id.meier.timetracking.util.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("authenticated")
class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	@Autowired
	private final AuthenticationUtils authenticationUtils;

	public ConfigureUIServiceInitListener(AuthenticationUtils authenticationUtils) {
		this.authenticationUtils = authenticationUtils;
	}

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::beforeEnter);
		});
	}

	/**
	 * Reroutes the user if (s)he is not authorized to access the view.
	 *
	 * @param event
	 *            before navigation event with event details
	 */
	private void beforeEnter(BeforeEnterEvent event) {
		if (!authenticationUtils.isUserLoggedIn() && !LoginView.class.equals(event.getNavigationTarget())
		    ) {
			event.rerouteTo(LoginView.class);
		}
	}
}