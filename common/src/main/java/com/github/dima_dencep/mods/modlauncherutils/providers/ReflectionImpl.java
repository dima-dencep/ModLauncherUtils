package com.github.dima_dencep.mods.modlauncherutils.providers;

import com.github.dima_dencep.mods.modlauncherutils.api.reflection.ReflectProvider;
import org.apiguardian.api.API;

import java.lang.reflect.Field;

/**
 * @deprecated Use {@link UnsafeImpl}
 */
@API(status = API.Status.DEPRECATED)
public class ReflectionImpl implements ReflectProvider {
    /**
     * @see ReflectProvider#initialize()
     */
    @Override
    @API(status = API.Status.DEPRECATED)
    public boolean initialize() {
        return false;
    }

    /**
     * @see ReflectProvider#isReady()
     */
    @Override
    @API(status = API.Status.DEPRECATED)
    public boolean isReady() {
        return true;
    }

    /**
     * @see ReflectProvider#getField(Field, Object)
     *
     * @deprecated Use {@link UnsafeImpl#getField(Field, Object)}
     */
    @Override
    @SuppressWarnings("unchecked")
    @API(status = API.Status.DEPRECATED)
    public <T> T getField(Field field, Object object) {
        field.setAccessible(true);

        try {
            return (T) field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see ReflectProvider#setField(Field, Object, Object)
     *
     * @deprecated Use {@link UnsafeImpl#setField(Field, Object, Object)}
     */
    @Override
    @API(status = API.Status.DEPRECATED)
    public void setField(Field data, Object object, Object value) {
        data.setAccessible(true);

        try {
            data.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see ReflectProvider#getIntField(Field, Object)
     *
     * @deprecated Use {@link UnsafeImpl#getIntField(Field, Object)}
     */
    @Override
    @API(status = API.Status.DEPRECATED)
    public int getIntField(Field field, Object object) {
        field.setAccessible(true);

        try {
            return field.getInt(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see ReflectProvider#setIntField(Field, Object, int)
     *
     * @deprecated Use {@link UnsafeImpl#setIntField(Field, Object, int)}
     */
    @Override
    @API(status = API.Status.DEPRECATED)
    public void setIntField(Field data, Object object, int value) {
        data.setAccessible(true);

        try {
            data.setInt(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
