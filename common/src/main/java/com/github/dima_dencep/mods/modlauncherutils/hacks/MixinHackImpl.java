package com.github.dima_dencep.mods.modlauncherutils.hacks;

import com.github.dima_dencep.mods.modlauncherutils.api.Initializer;
import com.github.dima_dencep.mods.modlauncherutils.api.hacks.MixinHacks;
import org.apiguardian.api.API;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.launch.platform.MixinConnectorManager;
import org.spongepowered.asm.launch.platform.MixinPlatformManager;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @see MixinHacks
 */
@API(status = API.Status.INTERNAL)
public class MixinHackImpl implements MixinHacks {
    private static final Initializer initializer = Initializer.getInstance();
    private static Field platformConnectorsField;
    private static Field connectorsField;

    /**
     * @see MixinHacks#injectMixinConnector(IMixinConnector)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public void injectMixinConnector(IMixinConnector mixinConnector) throws Exception {
        if (platformConnectorsField == null) {
            platformConnectorsField = MixinPlatformManager.class.getDeclaredField("connectors");
            platformConnectorsField.setAccessible(true);
        }

        MixinConnectorManager manager = initializer.getReflectProvider().getField(platformConnectorsField, MixinBootstrap.getPlatform());

        if (connectorsField == null) {
            connectorsField = MixinConnectorManager.class.getDeclaredField("connectors");
            connectorsField.setAccessible(true);
        }

        initializer.getReflectProvider().<List<IMixinConnector>>getField(connectorsField, manager).add(mixinConnector);
    }
}
