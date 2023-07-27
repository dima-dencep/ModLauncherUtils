package com.github.dima_dencep.mods.modlauncherutils.v10;

import com.github.dima_dencep.mods.modlauncherutils.api.Initializer;
import com.github.dima_dencep.mods.modlauncherutils.hacks.MixinHackImpl;
import com.github.dima_dencep.mods.modlauncherutils.providers.UnsafeImpl;
import com.github.dima_dencep.mods.modlauncherutils.v10.hacks.HacksImpl;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import org.apiguardian.api.API;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @see Initializer
 */
@API(status = API.Status.INTERNAL)
public class InitializerService extends Initializer implements ITransformationService {

    /**
     * @see Initializer
     */
    @API(status = API.Status.INTERNAL)
    public InitializerService() {
        INSTANCE = this;

        addReflectProvider(new UnsafeImpl());

        setMixinHacks(new MixinHackImpl());

        HacksImpl hacks = new HacksImpl();
        setModLauncherHacks(hacks);
        setSecureJarHacks(hacks);
    }

    /**
     * @see ITransformationService#name()
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public String name() {
        return "ModLauncherUtils";
    }

    /**
     * @see ITransformationService#initialize(IEnvironment)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public void initialize(IEnvironment environment) {

    }

    /**
     * @see ITransformationService#onLoad(IEnvironment, Set)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public void onLoad(IEnvironment env, Set<String> otherServices) {

    }

    /**
     * @see ITransformationService#transformers()
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public List<ITransformer> transformers() {
        return Collections.emptyList();
    }
}
