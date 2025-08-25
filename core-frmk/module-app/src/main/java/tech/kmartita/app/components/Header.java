package tech.kmartita.app.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import tech.kmartita.tools.AbstractComponent;
import tech.kmartita.tools.helpers.ui.elements.IButton;
import tech.kmartita.tools.helpers.ui.elements.Button;
import tech.kmartita.tools.helpers.ui.pagefactory.annotations.UiComponent;
import tech.kmartita.tools.helpers.ui.pagefactory.annotations.UiFind;

import java.util.List;

//Done:
// + check with invalid parent selector & invalid child selector
// + check List<Locator>
@UiComponent("div#app_") // _ - invalid to check failed test
public class Header extends AbstractComponent {

    @UiFind("img")
    private List<Locator> logos;

    @UiFind("img[alt='Selenium Online Training']")
    private Locator logo;

    public IButton button() {
        return new Button(logo);
    }

    @Step("Click on 'logo' image")
    public void clickOnLogo(){
        logo.click();
    }

    @Step("Check is 'logo' img present")
    public boolean isLogoPresent() {
        return logo.isVisible();
    }

    @Step("Click on wrapped button")
    public void clickOnButton() {
        button().click();
    }

    @Override
    protected void waitUntilLoaded() { }
}
