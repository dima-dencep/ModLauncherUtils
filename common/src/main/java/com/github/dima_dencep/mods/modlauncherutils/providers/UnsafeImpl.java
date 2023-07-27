package com.github.dima_dencep.mods.modlauncherutils.providers;

import com.github.dima_dencep.mods.modlauncherutils.api.reflection.ReflectProvider;
import org.apiguardian.api.API;

import java.lang.reflect.Field;

/**
 * @see ReflectProvider
 */
@API(status = API.Status.INTERNAL)
public class UnsafeImpl implements ReflectProvider {
    private sun.misc.Unsafe UNSAFE;

    /**
     * @see ReflectProvider#initialize()
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public boolean initialize() {
        try {
            final Field theUnsafe = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);

            UNSAFE = (sun.misc.Unsafe) theUnsafe.get(null);

            return true;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }


    /**
     * @see ReflectProvider#isReady()
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public boolean isReady() {
        return UNSAFE != null;
    }

    /**
     * @see ReflectProvider#getField(Field, Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    @API(status = API.Status.INTERNAL)
    public <T> T getField(Field field, Object object) {
        if (object == null) {
            long offset = UNSAFE.staticFieldOffset(field);
            Object base = UNSAFE.staticFieldBase(field);
            return (T) UNSAFE.getObject(base, offset);
        } else {
            long offset = UNSAFE.objectFieldOffset(field);
            return (T) UNSAFE.getObject(object, offset);
        }
    }

    /**
     * @see ReflectProvider#setField(Field, Object, Object)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public void setField(Field data, Object object, Object value) {
        if (object == null) {
            long offset = UNSAFE.staticFieldOffset(data);
            Object base = UNSAFE.staticFieldBase(data);
            UNSAFE.putObject(base, offset, value);
        } else {
            long offset = UNSAFE.objectFieldOffset(data);
            UNSAFE.putObject(object, offset, value);
        }
    }

    /**
     * @see ReflectProvider#getIntField(Field, Object)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public int getIntField(Field field, Object object) {
        if (object == null) {
            long offset = UNSAFE.staticFieldOffset(field);
            Object base = UNSAFE.staticFieldBase(field);
            return UNSAFE.getInt(base, offset);
        } else {
            long offset = UNSAFE.objectFieldOffset(field);
            return UNSAFE.getInt(object, offset);
        }
    }

    /**
     * @see ReflectProvider#setIntField(Field, Object, int)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public void setIntField(Field data, Object object, int value) {
        if (object == null) {
            long offset = UNSAFE.staticFieldOffset(data);
            Object base = UNSAFE.staticFieldBase(data);
            UNSAFE.putInt(base, offset, value);
        } else {
            long offset = UNSAFE.objectFieldOffset(data);
            UNSAFE.putInt(object, offset, value);
        }
    }
}
