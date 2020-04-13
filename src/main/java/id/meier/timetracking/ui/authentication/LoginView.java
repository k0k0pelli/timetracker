package id.meier.timetracking.ui.authentication;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Profile;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
@Profile("authenticated")
class LoginView extends VerticalLayout {
    static final String ROUTE = "login";

    public LoginView(){
        //
        LoginOverlay login = new LoginOverlay();
        login.setAction("login"); //
            login.setOpened(true); //
            login.setTitle("Login");
            login.setDescription("TimeTracker login required");
            getElement().appendChild(login.getElement()); //
        }
}

