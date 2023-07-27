package com.github.dima_dencep.mods.modlauncherutils.api.reflection;

import org.apiguardian.api.API;

import java.lang.reflect.Field;

@API(status = API.Status.STABLE)
public interface ReflectProvider {
    @API(status = API.Status.STABLE)
    boolean initialize();

    @API(status = API.Status.STABLE)
    boolean isReady();

    @API(status = API.Status.STABLE)
    <T> T getField(Field field, Object object);

    @API(status = API.Status.STABLE)
    void setField(Field data, Object object, Object value);

    @API(status = API.Status.EXPERIMENTAL)
    default int getIntField(Field field, Object object) {
        return this.getField(field, object);
    }

    @API(status = API.Status.EXPERIMENTAL)
    default void setIntField(Field data, Object object, int value) {
        this.setField(data, object, value);
    }
}
