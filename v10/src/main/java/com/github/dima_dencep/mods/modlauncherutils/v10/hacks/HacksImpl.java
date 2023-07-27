package com.github.dima_dencep.mods.modlauncherutils.v10.hacks;

import com.github.dima_dencep.mods.modlauncherutils.api.Initializer;
import com.github.dima_dencep.mods.modlauncherutils.api.hacks.ModLauncherHacks;
import com.github.dima_dencep.mods.modlauncherutils.api.hacks.SecureJarHacks;
import com.github.dima_dencep.mods.modlauncherutils.api.utils.CustomJarMetadata;
import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import cpw.mods.jarhandling.impl.Jar;
import cpw.mods.jarhandling.impl.ModuleJarMetadata;
import cpw.mods.jarhandling.impl.SimpleJarMetadata;
import cpw.mods.modlauncher.ArgumentHandler;
import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.ModuleLayerHandler;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import cpw.mods.modlauncher.api.INameMappingService;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.apiguardian.api.API;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @see ModLauncherHacks
 * @see SecureJarHacks
 */
@API(status = API.Status.INTERNAL)
public class HacksImpl implements ModLauncherHacks, SecureJarHacks {
    private static final Initializer initializer = Initializer.getInstance();
    private static Field launchPluginsField;
    private static Field pluginsField;

    /**
     * @see ModLauncherHacks#injectLaunchPlugin(ILaunchPluginService)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public void injectLaunchPlugin(ILaunchPluginService service) throws Exception {
        if (launchPluginsField == null) {
            launchPluginsField = Launcher.class.getDeclaredField("launchPlugins");
            launchPluginsField.setAccessible(true);
        }

        LaunchPluginHandler launchPlugins = initializer.getReflectProvider().getField(launchPluginsField, Launcher.INSTANCE);

        if (pluginsField == null) {
            pluginsField = LaunchPluginHandler.class.getDeclaredField("plugins");
            pluginsField.setAccessible(true);
        }

        initializer.getReflectProvider().<Map<String, ILaunchPluginService>>getField(pluginsField, launchPlugins).put(service.name(), service);
    }

    private static Field nameMappingServiceHandlerField;
    private static Field namingTableField;
    private static Constructor<?> decoratorConstructor;

    /**
     * @see ModLauncherHacks#injectNamingService(INameMappingService)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public void injectNamingService(INameMappingService service) throws Exception {
        if (nameMappingServiceHandlerField == null) {
            nameMappingServiceHandlerField = Launcher.class.getDeclaredField("nameMappingServiceHandler");
            nameMappingServiceHandlerField.setAccessible(true);
        }

        Object nameMappingServiceHandler = initializer.getReflectProvider().getField(nameMappingServiceHandlerField, Launcher.INSTANCE);

        if (namingTableField == null) {
            namingTableField = Class.forName("cpw.mods.modlauncher.NameMappingServiceHandler").getDeclaredField("namingTable");
            namingTableField.setAccessible(true);
        }

        Map<String, Object> namingTable = initializer.getReflectProvider().getField(namingTableField, nameMappingServiceHandler);

        if (decoratorConstructor == null) {
            decoratorConstructor = Class.forName("cpw.mods.modlauncher.NameMappingServiceDecorator").getDeclaredConstructor(INameMappingService.class);
            decoratorConstructor.setAccessible(true);
        }

        namingTable.put(service.mappingName(), decoratorConstructor.newInstance(service));
    }

    private static Field argumentHandlerField;
    private static Field argsField;

    /**
     * @see ModLauncherHacks#getArgs()
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public String[] getArgs() throws Exception {
        if (argumentHandlerField == null) {
            argumentHandlerField = Launcher.class.getDeclaredField("argumentHandler");
            argumentHandlerField.setAccessible(true);
        }

        ArgumentHandler argumentHandler = initializer.getReflectProvider().getField(argumentHandlerField, Launcher.INSTANCE);

        if (argsField == null) {
            argsField = ArgumentHandler.class.getDeclaredField("args");
            argsField.setAccessible(true);
        }

        return initializer.getReflectProvider().getField(argsField, argumentHandler);
    }

    private static Method addToLayerMethod;

    /**
     * @see SecureJarHacks#addJarToLayer(IModuleLayerManager.Layer, SecureJar)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public void addJarToLayer(IModuleLayerManager.Layer layer, SecureJar jar) throws Exception {
        if (addToLayerMethod == null) {
            addToLayerMethod = ModuleLayerHandler.class.getDeclaredMethod("addToLayer", IModuleLayerManager.Layer.class, SecureJar.class);
            addToLayerMethod.setAccessible(true);
        }

        addToLayerMethod.invoke(Launcher.INSTANCE.findLayerManager().orElseThrow(), layer, jar);
    }

    private static Field layersField;

    /**
     * @see SecureJarHacks#getLayerElements()
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public EnumMap<IModuleLayerManager.Layer, List<?>> getLayerElements() throws Exception {
        if (layersField == null) {
            layersField = ModuleLayerHandler.class.getDeclaredField("layers");
            layersField.setAccessible(true);
        }

        return initializer.getReflectProvider().getField(layersField, Launcher.INSTANCE.findLayerManager().orElseThrow());
    }

    private static Field jarField;

    /**
     * @see SecureJarHacks#getLayerJars()
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public List<SecureJar> getLayerJars() throws Exception {
        List<SecureJar> jars = new ArrayList<>();

        for (List<?> pathOrJarList : getLayerElements().values()) {
            for (Object pathOrJar : pathOrJarList) {
                if (jarField == null) {
                    jarField = pathOrJar.getClass().getDeclaredField("jar");
                    jarField.setAccessible(true);
                }

                SecureJar jar = (SecureJar) jarField.get(pathOrJar);

                if (jar != null) {
                    jars.add(jar);
                }
            }
        }

        return jars;
    }

    public static Field metadataField;

    /**
     * @see SecureJarHacks#getMetadataFromJar(Jar)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public JarMetadata getMetadataFromJar(Jar jar) throws Exception {
        if (metadataField == null) {
            metadataField = Jar.class.getDeclaredField("metadata");
            metadataField.setAccessible(true);
        }

        return initializer.getReflectProvider().getField(metadataField, jar);
    }

    /**
     * @see SecureJarHacks#createMetadataFromJar(SecureJar)
     */
    @Override
    @API(status = API.Status.INTERNAL)
    public JarMetadata createMetadataFromJar(SecureJar jar) {
        var mi = jar.moduleDataProvider().findFile("module-info.class");
        var pkgs = jar.getPackages();

        if (mi.isPresent()) {
            return new ModuleJarMetadata(mi.get(), pkgs);
        }

        var providers = jar.getProviders();
        var fileCandidate = JarMetadata.fromFileName(jar.getPrimaryPath(), pkgs, providers);

        var autoName = jar.moduleDataProvider().getManifest().getMainAttributes().getValue("Automatic-Module-Name");

        if (autoName != null) {
            return new SimpleJarMetadata(autoName, fileCandidate.version(), pkgs, providers);
        }

        return new CustomJarMetadata(jar, fileCandidate);
    }
}
