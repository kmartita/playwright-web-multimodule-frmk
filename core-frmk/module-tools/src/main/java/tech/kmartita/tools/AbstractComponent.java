package tech.kmartita.tools;

import com.microsoft.playwright.Page;
import tech.kmartita.tools.helpers.ui.pagefactory.PageFactory;

public abstract class AbstractComponent {

    protected final Page page;

    protected AbstractComponent() {
        this.page = AbstractApp.getPage();
        PageFactory.initComponents(this, page);

        waitUntilLoaded();
    }

    protected abstract void waitUntilLoaded();
}
