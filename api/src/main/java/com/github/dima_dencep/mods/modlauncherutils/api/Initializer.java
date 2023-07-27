package com.github.dima_dencep.mods.modlauncherutils.api;

import com.github.dima_dencep.mods.modlauncherutils.api.hacks.MixinHacks;
import com.github.dima_dencep.mods.modlauncherutils.api.hacks.ModLauncherHacks;
import com.github.dima_dencep.mods.modlauncherutils.api.hacks.SecureJarHacks;
import com.github.dima_dencep.mods.modlauncherutils.api.reflection.ReflectProvider;
import org.apiguardian.api.API;

import java.util.ArrayList;
import java.util.List;

@API(status = API.Status.STABLE)
public class Initializer {
    private static final List<ReflectProvider> reflectProviders = new ArrayList<>();
    protected static Initializer INSTANCE;
    private ModLauncherHacks modLauncherHacks;
    private ReflectProvider reflectProvider;
    private SecureJarHacks secureJarHacks;
    private MixinHacks mixinHacks;

    @API(status = API.Status.STABLE)
    public ReflectProvider getReflectProvider() {
        if (this.reflectProvider == null) {

            for (ReflectProvider provider : reflectProviders) {
                if (provider.initialize()) {
                    this.reflectProvider = provider;

                    break;
                }
            }
        }

        return this.reflectProvider;
    }

    @API(status = API.Status.STABLE)
    public ModLauncherHacks getModLauncherHacks() {
        return this.modLauncherHacks;
    }

    @API(status = API.Status.STABLE)
    public SecureJarHacks getSecureJarHacks() {
        return this.secureJarHacks;
    }

    @API(status = API.Status.STABLE)
    public MixinHacks getMixinHacks() {
        return this.mixinHacks;
    }

    @API(status = API.Status.INTERNAL)
    protected void setMixinHacks(MixinHacks mixinHacks) {
        if (this.mixinHacks != null) {
            throw new IllegalStateException("The hacks for Mixin are already installed!");
        }

        this.mixinHacks = mixinHacks;
    }

    @API(status = API.Status.INTERNAL)
    protected void setSecureJarHacks(SecureJarHacks secureJarHacks) {
        if (this.secureJarHacks != null) {
            throw new IllegalStateException("The hacks for SecureJarHandler are already installed!");
        }

        this.secureJarHacks = secureJarHacks;
    }

    @API(status = API.Status.INTERNAL)
    protected void setModLauncherHacks(ModLauncherHacks modLauncherHacks) {
        if (this.modLauncherHacks != null) {
            throw new IllegalStateException("The hacks for ModLauncher are already installed!");
        }

        this.modLauncherHacks = modLauncherHacks;
    }

    @API(status = API.Status.EXPERIMENTAL)
    public static void addReflectProvider(ReflectProvider reflectProvider) {
        addReflectProvider(reflectProvider, false);
    }

    @API(status = API.Status.EXPERIMENTAL)
    public static void addReflectProvider(ReflectProvider reflectProvider, boolean force) {
        reflectProviders.add(reflectProvider);

        if (force && reflectProvider.isReady()) {
            if (INSTANCE != null)
                INSTANCE.reflectProvider = reflectProvider;
        }
    }

    @API(status = API.Status.STABLE)
    public static Initializer getInstance() {
        return INSTANCE;
    }
}
