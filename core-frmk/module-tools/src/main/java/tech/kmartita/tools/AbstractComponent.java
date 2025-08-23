package tech.kmartita.tools;

import com.microsoft.playwright.Page;
import tech.kmartita.tools.helpers.ui.pagefactory.Factory;

public abstract class AbstractComponent {

    protected final Page page;

    protected AbstractComponent() {
        this.page = AbstractApp.getPage();
        Factory.initComponents(this, page);

        waitUntilLoaded();
    }

    protected abstract void waitUntilLoaded();
}
