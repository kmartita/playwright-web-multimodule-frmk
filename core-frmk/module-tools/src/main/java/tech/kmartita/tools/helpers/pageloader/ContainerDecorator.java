package tech.kmartita.tools.helpers.pageloader;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import tech.kmartita.tools.helpers.pageloader.annotations.UiFind;

import java.lang.reflect.Field;
import java.util.List;

public class ContainerDecorator implements IDecorator {

    Page page;
    LocatorFactory locatorFactory;

    public ContainerDecorator(Page page) {
        this.page = page;
        this.locatorFactory = new LocatorFactory(page);
    }

    @Override
    public Object decorate(Field field) {
        Locator container = locatorFactory.getContainerLocator(field);
        if (field.isAnnotationPresent(UiFind.class)) {
            String selector = field.getAnnotation(UiFind.class).value();

            return field.getType().equals(List.class)
                    ? locatorFactory.getLocators(container.locator(selector))
                    : container.locator(selector);
        } else {
            throw new RuntimeException("Field must be annotated with @UiFind\n.");
        }
    }
}
