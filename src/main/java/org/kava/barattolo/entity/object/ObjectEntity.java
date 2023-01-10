package org.kava.barattolo.entity.object;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public record ObjectEntity(Class<?> objectClass,
                           List<ObjectField> fields) {
    public Object toObject() {
        try {
            Object mappedObject = objectClass.getConstructor().newInstance();
            for (ObjectField field : fields) {
                field.fieldDefinition().field().setAccessible(true);
                field.fieldDefinition().field().set(mappedObject, field.value());
            }

            return mappedObject;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
