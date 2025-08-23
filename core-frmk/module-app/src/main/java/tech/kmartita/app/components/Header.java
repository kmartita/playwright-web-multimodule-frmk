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
@UiComponent("div#app")
public class Header extends AbstractComponent {

    @UiFind("img")
    private List<Locator> logos;

    @UiFind("img[alt='Selenium Online Training']")
    private Locator logo;

    public IButton button() {
        return new Button(logo);
    }

    public void clickOn(){
        logos.forEach(logo -> logo.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)));
        logos.get(0).click();
    }

    @Step("Is...")
    public boolean isLogoPresent() {
        logo.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return logo.isVisible();
    }

    @Step("Click On Button")
    public void clickOnButton() {
        button().click();
    }

    @Override
    protected void waitUntilLoaded() { }
}
