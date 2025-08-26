package tech.kmartita.tools;

import com.microsoft.playwright.Page;
import tech.kmartita.tools.helpers.pageloader.PageLoader;

public abstract class AbstractComponent {

    protected final Page page;

    protected AbstractComponent() {
        this.page = AbstractApp.getPage();
        PageLoader.initComponents(this, page);

        waitUntilLoaded();
    }

    protected abstract void waitUntilLoaded();
}
