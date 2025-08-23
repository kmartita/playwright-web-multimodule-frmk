package tech.kmartita.app;

import io.qameta.allure.Step;
import tech.kmartita.app.pages.HomePage;
import tech.kmartita.tools.AbstractApp;

import static tech.kmartita.tools.helpers.EnvManager.BASE_URL;

public class WebApp extends AbstractApp {

    public WebApp() {
        startSession();
    }

    @Step("Open 'Home Page'")
    public HomePage openNewLoginPage() {
        getPage().navigate(BASE_URL);
        return new HomePage();
    }
}