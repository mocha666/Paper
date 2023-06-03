package io.papermc.generator;

import com.mojang.logging.LogUtils;
import io.papermc.generator.types.GeneratedKeyType;
import io.papermc.generator.types.SourceGenerator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.minecraft.SharedConstants;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import org.apache.commons.io.file.PathUtils;
import org.bukkit.GameEvent;
import org.bukkit.block.Biome;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.slf4j.Logger;

public final class Main {

    private static final Logger LOGGER = LogUtils.getLogger();
    public static final RegistryAccess.Frozen REGISTRY_ACCESS;

    static {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        // Populate available packs
        PackRepository resourceRepository = new PackRepository(new ServerPacksSource());
        resourceRepository.reload();
        // Set up resource manager
        MultiPackResourceManager resourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, resourceRepository.getAvailablePacks().stream().map(Pack::open).toList());
        // add tags and loot tables for unit tests
        LayeredRegistryAccess<RegistryLayer> layers = RegistryLayer.createRegistryAccess();
        layers = WorldLoader.loadAndReplaceLayer(resourceManager, layers, RegistryLayer.WORLDGEN, RegistryDataLoader.WORLDGEN_REGISTRIES);
        REGISTRY_ACCESS = layers.compositeAccess().freeze();
    }

    private static final List<SourceGenerator> GENERATORS = List.of(
        simpleKey("GameEventKeys", GameEvent.class, Registries.GAME_EVENT),
        simpleKey("BiomeKeys", Biome.class, Registries.BIOME),
        simpleKey("TrimMaterialKeys", TrimMaterial.class, Registries.TRIM_MATERIAL),
        simpleKey("TrimPatternKeys", TrimPattern.class, Registries.TRIM_PATTERN)
    );

    private static <T> SourceGenerator simpleKey(final String className, final Class<? extends Keyed> apiType, final ResourceKey<? extends Registry<T>> registryKey) {
        return new GeneratedKeyType<>(className, apiType, "io.papermc.paper.generated", registryKey);
    }

    private Main() {
    }

    public static void main(final String[] args) {
        final Path output = Paths.get(args[0]);
        try {
            if (Files.exists(output)) {
                PathUtils.deleteDirectory(output);
            }
            Files.createDirectories(output);

            for (final SourceGenerator generator : GENERATORS) {
                generator.writeToFile(output);
            }

            LOGGER.info("Files written to {}", output.toAbsolutePath());
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}
