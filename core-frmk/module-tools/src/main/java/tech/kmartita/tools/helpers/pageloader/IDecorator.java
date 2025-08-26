package tech.kmartita.tools.helpers.pageloader;

import java.lang.reflect.Field;

public interface IDecorator {
    Object decorate(Field field);
}
