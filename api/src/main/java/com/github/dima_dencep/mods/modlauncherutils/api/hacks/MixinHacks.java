package com.github.dima_dencep.mods.modlauncherutils.api.hacks;

import org.apiguardian.api.API;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

/**
 * Hacks designed for Mixin.
 */
@API(status = API.Status.STABLE)
public interface MixinHacks {

    /**
     * Allows {@link IMixinConnector} to be added directly to mixin.
     *
     * @param mixinConnector To add
     * @throws Exception If the reflection failed
     */
    @API(status = API.Status.STABLE)
    void injectMixinConnector(IMixinConnector mixinConnector) throws Exception;
}
