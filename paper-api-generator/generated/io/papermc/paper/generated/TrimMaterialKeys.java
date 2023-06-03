package io.papermc.paper.generated;

import static net.kyori.adventure.key.Key.key;

import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import org.bukkit.MinecraftExperimental;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Vanilla keys for TrimMaterial.
 */
@SuppressWarnings({
        "unused",
        "SpellCheckingInspection"
})
@GeneratedFrom("1.19.4")
@ApiStatus.Experimental
@MinecraftExperimental
public final class TrimMaterialKeys {
    /**
     * {@link TrimMaterial} key: {@code minecraft:amethyst}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimMaterial> AMETHYST = create(key("amethyst"));

    /**
     * {@link TrimMaterial} key: {@code minecraft:copper}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimMaterial> COPPER = create(key("copper"));

    /**
     * {@link TrimMaterial} key: {@code minecraft:diamond}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimMaterial> DIAMOND = create(key("diamond"));

    /**
     * {@link TrimMaterial} key: {@code minecraft:emerald}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimMaterial> EMERALD = create(key("emerald"));

    /**
     * {@link TrimMaterial} key: {@code minecraft:gold}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimMaterial> GOLD = create(key("gold"));

    /**
     * {@link TrimMaterial} key: {@code minecraft:iron}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimMaterial> IRON = create(key("iron"));

    /**
     * {@link TrimMaterial} key: {@code minecraft:lapis}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimMaterial> LAPIS = create(key("lapis"));

    /**
     * {@link TrimMaterial} key: {@code minecraft:netherite}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimMaterial> NETHERITE = create(key("netherite"));

    /**
     * {@link TrimMaterial} key: {@code minecraft:quartz}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimMaterial> QUARTZ = create(key("quartz"));

    /**
     * {@link TrimMaterial} key: {@code minecraft:redstone}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimMaterial> REDSTONE = create(key("redstone"));

    private TrimMaterialKeys() {
    }

    @ApiStatus.Experimental
    @MinecraftExperimental
    public static @NotNull TypedKey<TrimMaterial> create(final @NotNull Key key) {
        return TypedKey.create(key/*, <insert registry key here from reg mod API> */);
    }
}
