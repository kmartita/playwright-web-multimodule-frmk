package tech.kmartita.tools.helpers.htmlelements;

import com.microsoft.playwright.Locator;
import tech.kmartita.tools.AbstractUiElement;

public class Button extends AbstractUiElement implements IButton {

    public Button(Locator locator) {
        super(locator);
    }
}
