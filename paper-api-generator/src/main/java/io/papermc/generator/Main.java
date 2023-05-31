package io.papermc.generator;

import com.mojang.logging.LogUtils;
import io.papermc.generator.types.GeneratedKeyType;
import io.papermc.generator.types.SourceGenerator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import net.minecraft.SharedConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.Bootstrap;
import org.apache.commons.io.file.PathUtils;
import org.slf4j.Logger;

public final class Main {

    static {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final List<SourceGenerator> GENERATORS = List.of(
        new GeneratedKeyType("GameEventKeys", "GameEvent", "io.papermc.paper.generated", BuiltInRegistries.GAME_EVENT)
    );

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
