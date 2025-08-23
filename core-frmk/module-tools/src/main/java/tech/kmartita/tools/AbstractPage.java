package tech.kmartita.tools;

import com.microsoft.playwright.Page;
import tech.kmartita.tools.helpers.ui.pagefactory.Factory;

public abstract class AbstractPage {

    protected final Page page;

    protected AbstractPage() {
        this.page = AbstractApp.getPage();
        Factory.initPages(this, page);

        waitUntilLoaded();
    }

    //add wrappers Explicit Waits for Page

    protected abstract void waitUntilLoaded();
}
