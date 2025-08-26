package tech.kmartita.tools.helpers.pageloader;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import tech.kmartita.tools.helpers.pageloader.annotations.UiComponent;
import tech.kmartita.tools.helpers.pageloader.annotations.UiPage;
import tech.kmartita.tools.helpers.pageloader.annotations.UiFind;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class LocatorFactory {

    Page page;

    public LocatorFactory(Page page) {
        this.page = page;
    }

    public Locator getContainerLocator(Field field) {
        Class<?> clazz = field.getDeclaringClass();
        UiComponent componentAnnotation = clazz.getAnnotation(UiComponent.class);

        if (clazz.isAnnotationPresent(UiComponent.class)) {
            String containerSelector = componentAnnotation.value();
            return page.locator(containerSelector);

        } else {
            throw new RuntimeException("Class must be annotated with @UiComponent.\n");
        }
    }

    public Locator getPageLocator(Field field) {
        Class<?> clazz = field.getDeclaringClass();
        UiPage pageAnnotation = clazz.getAnnotation(UiPage.class);
        String selector = field.getAnnotation(UiFind.class).value();

        if (clazz.isAnnotationPresent(UiPage.class)) {
            if (field.isAnnotationPresent(UiFind.class)) {
                if (pageAnnotation.frame().length == 0) {
                    return page.locator(selector);
                }

                FrameLocator frameLocator = page.frameLocator(pageAnnotation.frame()[0]);
                if (pageAnnotation.frame().length > 1) {
                    for (int i = 1; i < pageAnnotation.frame().length; i++) {
                        frameLocator = frameLocator.frameLocator(pageAnnotation.frame()[i]);
                    }
                }
                return frameLocator.locator(selector);
            } else {
                throw new RuntimeException("Field must be annotated with @UiFind.\n");
            }
        } else {
            throw new RuntimeException("Class must be annotated with @UiPage.\n");
        }
    }

    public List<Locator> getLocators(Locator locator) {
        Locator[] locatorsArray = locator.all().toArray(new Locator[0]);
        return Arrays.asList(locatorsArray);
    }
}
