package tech.kmartita.app.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import tech.kmartita.tools.AbstractPage;
import tech.kmartita.tools.helpers.ui.pagefactory.annotations.UiFind;
import tech.kmartita.tools.helpers.ui.pagefactory.annotations.UiPage;

import java.util.List;

//Done:
// + check with invalid selector for @UiFind
// + check List<Locator> + invalid selector for @UiFind
// - check frames
@UiPage
public class HomePage extends AbstractPage {

    @UiFind("img")
    private List<Locator> logos;

    @UiFind("img[alt='Selenium Online Training']")
    private Locator logo;

    @Step("Click on one of 'logo' images")
    public void clickOnFirstLogo(){
        //logos.forEach(logo -> logo.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)));
        logos.get(0).click();
    }

    @Step("Check is 'logo' img present")
    public boolean isLogoPresent() {
        //logo.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return logo.isVisible();
    }

    @Override
    protected void waitUntilLoaded() {
        page.waitForLoadState(LoadState.LOAD);
    }
}

