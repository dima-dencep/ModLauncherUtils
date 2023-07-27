package com.github.dima_dencep.mods.modlauncherutils.api.utils;

import com.github.dima_dencep.mods.modlauncherutils.api.Initializer;
import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import org.apiguardian.api.API;

import java.lang.module.ModuleDescriptor;

/**
 * Designed to eliminate crash due to duplicate packages.
 */
@API(status = API.Status.STABLE)
public class CustomJarMetadata implements JarMetadata {
    private static final ThreadLocal<Boolean> RE_ENTRANCE_LOCK = ThreadLocal.withInitial(() -> false);
    private final JarMetadata delegate;
    private final SecureJar secureJar;

    @API(status = API.Status.STABLE)
    public CustomJarMetadata(SecureJar secureJar, JarMetadata delegate) {
        this.secureJar = secureJar;
        this.delegate = delegate;
    }

    /**
     * @see JarMetadata#name()
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public String name() {
        if (RE_ENTRANCE_LOCK.get()) {
            throw new ModLauncherWasFuckedException();
        } else {
            RE_ENTRANCE_LOCK.set(true);

            try {
                for (SecureJar otherJar : Initializer.getInstance().getSecureJarHacks().getLayerJars()) {
                    if (otherJar != this.secureJar && otherJar.getPackages().stream().anyMatch(this.secureJar.getPackages()::contains)) {
                        String otherModuleName;
                        try {
                            otherModuleName = otherJar.name();
                        } catch (ModLauncherWasFuckedException var11) {
                            continue;
                        }

                        Initializer.LOGGER.info("Found existing module with name %s, renaming %s to match.", otherModuleName, this.delegate.name());
                        return otherModuleName;
                    }
                }
            } catch (Throwable t) {
                Initializer.LOGGER.error("Exception occurred while trying to self-rename module %s: %s", this.delegate.name(), t);
            }
        }


        return this.delegate.name();
    }

    /**
     * @see JarMetadata#version()
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public String version() {
        return this.delegate.version();
    }

    /**
     * @see JarMetadata#descriptor()
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public ModuleDescriptor descriptor() {
        return this.delegate.descriptor();
    }

    /**
     * @see CustomJarMetadata#name()
     */
    @API(status = API.Status.INTERNAL)
    private static class ModLauncherWasFuckedException extends RuntimeException {
    }
}
