package tech.kmartita.tools;

import com.microsoft.playwright.Page;
import tech.kmartita.tools.helpers.ui.pagefactory.PageFactory;

public abstract class AbstractPage {

    protected final Page page;

    protected AbstractPage() {
        this.page = AbstractApp.getPage();
        PageFactory.initPages(this, page);

        waitUntilLoaded();
    }

    //add wrappers Explicit Waits for Page

    protected abstract void waitUntilLoaded();
}
