package com.github.dima_dencep.mods.modlauncherutils.api.hacks;

import cpw.mods.modlauncher.api.INameMappingService;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.apiguardian.api.API;

/**
 * Hacks designed for ModLauncher.
 */
@API(status = API.Status.STABLE)
public interface ModLauncherHacks {

    /**
     * Allows {@link ILaunchPluginService} to be added directly to ModLauncher.
     *
     * @param service To add
     * @throws Exception If the reflection failed
     */
    @API(status = API.Status.STABLE)
    void injectLaunchPlugin(ILaunchPluginService service) throws Exception;

    /**
     * Allows {@link INameMappingService} to be added directly to ModLauncher.
     *
     * @param service To add
     * @throws Exception If the reflection failed
     */
    @API(status = API.Status.STABLE)
    void injectNamingService(INameMappingService service) throws Exception;

    /**
     * Getting all launch arguments from Modlauncher
     *
     * @return launch args
     * @throws Exception If the reflection failed
     */
    @API(status = API.Status.STABLE)
    String[] getArgs() throws Exception;
}
