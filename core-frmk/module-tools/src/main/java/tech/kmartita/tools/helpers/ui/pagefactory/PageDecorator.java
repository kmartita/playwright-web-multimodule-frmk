package tech.kmartita.tools.helpers.ui.pagefactory;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.lang.reflect.Field;
import java.util.List;

public class PageDecorator implements IDecorator {

    Page page;
    LocatorFactory locatorFactory;

    public PageDecorator(Page page) {
        this.page = page;
        this.locatorFactory = new LocatorFactory(page);
    }

    @Override
    public Object decorate(Field field) {
        Locator page = locatorFactory.getPageLocator(field);

        return field.getType().equals(List.class)
                ? locatorFactory.getLocators(page)
                : page;
    }
}
