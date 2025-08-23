package tech.kmartita.tools;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;

public abstract class AbstractUiElement implements ILocator {

    protected final Locator locator;

    public AbstractUiElement(Locator locator) {
        this.locator = locator;
    }

    @Override
    @Step("click")
    public void click() {
        this.locator.click();
    }
}
