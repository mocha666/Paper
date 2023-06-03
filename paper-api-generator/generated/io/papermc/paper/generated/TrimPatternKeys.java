package io.papermc.paper.generated;

import static net.kyori.adventure.key.Key.key;

import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import org.bukkit.MinecraftExperimental;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Vanilla keys for {@link TrimPattern}.
 */
@SuppressWarnings({
        "unused",
        "SpellCheckingInspection"
})
@GeneratedFrom("1.19.4")
@ApiStatus.Experimental
@MinecraftExperimental
public final class TrimPatternKeys {
    /**
     * {@code minecraft:coast}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> COAST = create(key("coast"));

    /**
     * {@code minecraft:dune}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> DUNE = create(key("dune"));

    /**
     * {@code minecraft:eye}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> EYE = create(key("eye"));

    /**
     * {@code minecraft:rib}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> RIB = create(key("rib"));

    /**
     * {@code minecraft:sentry}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> SENTRY = create(key("sentry"));

    /**
     * {@code minecraft:snout}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> SNOUT = create(key("snout"));

    /**
     * {@code minecraft:spire}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> SPIRE = create(key("spire"));

    /**
     * {@code minecraft:tide}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> TIDE = create(key("tide"));

    /**
     * {@code minecraft:vex}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> VEX = create(key("vex"));

    /**
     * {@code minecraft:ward}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> WARD = create(key("ward"));

    /**
     * {@code minecraft:wild}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> WILD = create(key("wild"));

    private TrimPatternKeys() {
    }

    /**
     * Creates a typed key for {@link TrimPattern}.
     *
     * @param key the key for the object
     * @returns a new typed key
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static @NotNull TypedKey<TrimPattern> create(final @NotNull Key key) {
        return TypedKey.create(key/*, <insert registry key here from reg mod API> */);
    }
}
