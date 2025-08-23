package tech.kmartita.tools.helpers.ui.pagefactory;

import java.lang.reflect.Field;

public interface IDecorator {
    Object decorate(Field field);
}
