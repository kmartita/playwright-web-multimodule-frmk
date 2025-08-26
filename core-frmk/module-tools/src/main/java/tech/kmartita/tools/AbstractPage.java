package tech.kmartita.tools;

import com.microsoft.playwright.Page;
import tech.kmartita.tools.helpers.pageloader.PageLoader;

public abstract class AbstractPage {

    protected final Page page;

    protected AbstractPage() {
        this.page = AbstractApp.getPage();
        PageLoader.initPages(this, page);

        waitUntilLoaded();
    }

    //add wrappers Explicit Waits for Page

    protected abstract void waitUntilLoaded();
}
