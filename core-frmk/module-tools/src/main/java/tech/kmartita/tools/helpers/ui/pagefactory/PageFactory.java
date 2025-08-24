package tech.kmartita.tools.helpers.ui.pagefactory;

import com.microsoft.playwright.Page;
import tech.kmartita.tools.helpers.ui.pagefactory.annotations.UiComponent;
import tech.kmartita.tools.helpers.ui.pagefactory.annotations.UiFind;
import tech.kmartita.tools.helpers.ui.pagefactory.annotations.UiPage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageFactory {

    public static void initComponents(Object pageObject, Page page) {
        if (!pageObject.getClass().isAnnotationPresent(UiComponent.class)) {
            throw new RuntimeException("Only components marked with @UiComponent can be initialized by the PageFactory.\n");
        }
        initFields(pageObject, new ContainerDecorator(page));
    }

    public static void initPages(Object pageObject, Page page) {
        if (!pageObject.getClass().isAnnotationPresent(UiPage.class)) {
            throw new RuntimeException("Only pages marked with @UiPage can be initialized by the PageFactory.\n");
        }
        initFields(pageObject, new PageDecorator(page));
    }

    private static void initFields(Object pageObject, IDecorator decorator) {
        if (pageObject.getClass().isAnnotationPresent(UiPage.class) && pageObject.getClass().isAnnotationPresent(UiComponent.class)) {
            throw new RuntimeException("Invalid configuration: @UiPage and @UiComponent cannot be used together on the same class.\n");
        }
        List<Class<?>> classes = new ArrayList<>();
        Class<?> clazz = pageObject.getClass();

        while (clazz != Object.class) {
            classes.add(clazz);
            clazz = clazz.getSuperclass();
        }
        Collections.reverse(classes);
        proxyFields(decorator, pageObject, classes);
    }

    private static void proxyFields(IDecorator decorator, Object pageObject, List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            for (Field field : clazz.getDeclaredFields()) {
                if (isALocator(field)) {
                    setField(decorator, field, pageObject);
                }
            }
        }
    }

    private static boolean isALocator(Field field) {
        return field.isAnnotationPresent(UiFind.class);
    }

    private static void setField(IDecorator decorator, Field field, Object pageObject) {
        Object value = decorator.decorate(field);
        if (value != null) {
            try {
                field.setAccessible(true);
                field.set(pageObject, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

