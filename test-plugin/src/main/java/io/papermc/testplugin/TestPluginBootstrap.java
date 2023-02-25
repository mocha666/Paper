package io.papermc.testplugin;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.registry.event.listener.RegistryAdditionListener;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.RegistryManager;
import io.papermc.paper.registry.event.RegistryPreFreezeEvent;
import io.papermc.paper.registry.event.RegistryAdditionEvent;
import io.papermc.paper.registry.event.listener.RegistryPreFreezeListener;
import org.bukkit.GameEvent;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.jetbrains.annotations.NotNull;

@DefaultQualifier(NonNull.class)
public class TestPluginBootstrap implements PluginBootstrap {

    static final NamespacedKey NEW_EVENT = new NamespacedKey("machine_maker", "best_event");
    static final NamespacedKey TO_MODIFY = NamespacedKey.minecraft("block_open");

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        final RegistryManager manager = context.getRegistryManager();
        manager.registerPreFreezeListener(RegistryKey.GAME_EVENT, new Test());
        manager.registerAdditionListener(RegistryKey.GAME_EVENT, new Test2());
    }

    private static final class Test implements RegistryPreFreezeListener<GameEvent, GameEvent.Builder> {
        @Override
        public void beforeFreeze(final RegistryPreFreezeEvent<GameEvent, GameEvent.Builder> event) {
            event.registryView().register(NEW_EVENT, builder -> {
                builder.range(2);
            });
        }
    }

    private static final class Test2 implements RegistryAdditionListener<GameEvent, GameEvent.Builder> {
        @Override
        public void beforeRegister(final RegistryAdditionEvent<GameEvent, GameEvent.Builder> event) {
            if (event.builder().key().equals(TO_MODIFY)) {
                event.builder().range(event.builder().range() * 2);
            }
        }
    }
}
