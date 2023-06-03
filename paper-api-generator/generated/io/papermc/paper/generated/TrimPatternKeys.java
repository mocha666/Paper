package io.papermc.paper.generated;

import static net.kyori.adventure.key.Key.key;

import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import org.bukkit.MinecraftExperimental;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Vanilla keys for TrimPattern.
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
     * {@link TrimPattern} key: {@code minecraft:coast}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> COAST = create(key("coast"));

    /**
     * {@link TrimPattern} key: {@code minecraft:dune}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> DUNE = create(key("dune"));

    /**
     * {@link TrimPattern} key: {@code minecraft:eye}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> EYE = create(key("eye"));

    /**
     * {@link TrimPattern} key: {@code minecraft:rib}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> RIB = create(key("rib"));

    /**
     * {@link TrimPattern} key: {@code minecraft:sentry}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> SENTRY = create(key("sentry"));

    /**
     * {@link TrimPattern} key: {@code minecraft:snout}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> SNOUT = create(key("snout"));

    /**
     * {@link TrimPattern} key: {@code minecraft:spire}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> SPIRE = create(key("spire"));

    /**
     * {@link TrimPattern} key: {@code minecraft:tide}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> TIDE = create(key("tide"));

    /**
     * {@link TrimPattern} key: {@code minecraft:vex}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> VEX = create(key("vex"));

    /**
     * {@link TrimPattern} key: {@code minecraft:ward}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> WARD = create(key("ward"));

    /**
     * {@link TrimPattern} key: {@code minecraft:wild}
     */
    @ApiStatus.Experimental
    @MinecraftExperimental
    public static final TypedKey<TrimPattern> WILD = create(key("wild"));

    private TrimPatternKeys() {
    }

    @ApiStatus.Experimental
    @MinecraftExperimental
    public static @NotNull TypedKey<TrimPattern> create(final @NotNull Key key) {
        return TypedKey.create(key/*, <insert registry key here from reg mod API> */);
    }
}
