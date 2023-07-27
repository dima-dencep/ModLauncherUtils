package com.github.dima_dencep.mods.modlauncherutils.api.hacks;

import cpw.mods.jarhandling.JarMetadata;
import cpw.mods.jarhandling.SecureJar;
import cpw.mods.jarhandling.impl.Jar;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import org.apiguardian.api.API;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.List;

/**
 * Hacks designed for SecureJarHandler.
 */
@API(status = API.Status.STABLE)
public interface SecureJarHacks {

    /**
     * Adding a {@link SecureJar} to a specific {@link IModuleLayerManager.Layer} in the classpath.
     *
     * @param layer The {@link IModuleLayerManager.Layer} to be added to
     * @param jar {@link SecureJar} to be added
     * @throws Exception If the reflection failed
     *
     * @see #addToClassPath(Path)
     */
    @API(status = API.Status.STABLE)
    void addJarToLayer(IModuleLayerManager.Layer layer, SecureJar jar) throws Exception;

    /**
     * Adding a {@link Path} to the classpath.
     *
     * @param path To add
     * @throws Exception If the reflection failed
     *
     * @see #addJarToLayer(IModuleLayerManager.Layer, SecureJar)
     */
    @API(status = API.Status.EXPERIMENTAL)
    default void addToClassPath(Path path) throws Exception {
        SecureJar jar = SecureJar.from(this::createMetadataFromJar, path);

        try {
            addJarToLayer(IModuleLayerManager.Layer.GAME, jar);
        } catch (Exception ex) {
            addJarToLayer(IModuleLayerManager.Layer.PLUGIN, jar);
        }
    }

    /**
     * Allows you to get the classpath from a specific {@link IModuleLayerManager.Layer}.
     *
     * @throws Exception If the reflection failed
     */
    @API(status = API.Status.EXPERIMENTAL)
    EnumMap<IModuleLayerManager.Layer, List<?>> getLayerElements() throws Exception;

    /**
     * Allows you to get the classpath.
     * Simulate {@link LaunchClassLoader#getSources()}.
     *
     * @throws Exception If the reflection failed
     */
    @API(status = API.Status.EXPERIMENTAL)
    List<SecureJar> getLayerJars() throws Exception;

    /**
     * Allows you to retrieve already created {@link JarMetadata} from {@link Jar}.
     *
     * @param jar from which you want to get {@link JarMetadata}
     * @throws Exception If the reflection failed
     */
    @API(status = API.Status.EXPERIMENTAL)
    JarMetadata getMetadataFromJar(Jar jar) throws Exception;

    /**
     * Allows you to create {@link JarMetadata} to avoid package conflicts.
     *
     * @param jar for which JarMetadata will be used
     */
    @API(status = API.Status.EXPERIMENTAL)
    JarMetadata createMetadataFromJar(SecureJar jar);

    /**
     * @deprecated Was created to simulate {@link LaunchClassLoader#addURL(URL)}.
     */
    @API(status = API.Status.DEPRECATED)
    default void addURL(URL url) throws Exception {
        addToClassPath(Paths.get(url.toURI()));
    }
}
