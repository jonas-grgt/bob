package io.jonasg.bob;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public abstract class AbstractBuilder<T> implements Builder<T> {

    protected T instance;

    public AbstractBuilder() {
        instance = newInstance();
    }

    abstract protected T newInstance();
    abstract public T build();

    protected void setField(String name, Object value) {
        try {
            Field field = instance.getClass().
                    getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void setField(Object instance, String name, Object value) {
        try {
            Field field = instance.getClass().
                    getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
