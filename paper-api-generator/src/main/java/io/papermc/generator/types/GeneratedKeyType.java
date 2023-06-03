package io.papermc.generator.types;

import com.mojang.serialization.Lifecycle;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.papermc.generator.Main;
import io.papermc.paper.registry.TypedKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.minecraft.SharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.registries.UpdateOneTwentyRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import org.bukkit.MinecraftExperimental;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@DefaultQualifier(NonNull.class)
public class GeneratedKeyType<T> implements SourceGenerator {

    private static final Map<ResourceKey<? extends Registry<?>>, RegistrySetBuilder.RegistryBootstrap<?>> EXPERIMENTAL_REGISTRY_ENTRIES = UpdateOneTwentyRegistries.BUILDER.entries.stream()
            .collect(Collectors.toMap(RegistrySetBuilder.RegistryStub::key, RegistrySetBuilder.RegistryStub::bootstrap));

    record CollectingContext<T>(List<ResourceKey<T>> registered, Registry<T> registry) implements BootstapContext<T> {

        @Override
        public Holder.Reference<T> register(final ResourceKey<T> resourceKey, final @NonNull T t, final Lifecycle lifecycle) {
            this.registered.add(resourceKey);
            return Holder.Reference.createStandAlone(this.registry.holderOwner(), resourceKey);
        }

        @Override
        public <S> HolderGetter<S> lookup(final ResourceKey<? extends Registry<? extends S>> resourceKey) {
            return Main.REGISTRY_ACCESS.registryOrThrow(resourceKey).asLookup();
        }
    }

    private static final List<AnnotationSpec> EXPERIMENTAL_ANNOTATIONS = List.of(
        AnnotationSpec.builder(ApiStatus.Experimental.class).build(),
        AnnotationSpec.builder(MinecraftExperimental.class).build()
    );

    private final String keysClassName;
    private final Class<?> apiType;
    private final String pkg;
    private final ResourceKey<? extends Registry<T>> registryKey;

    public GeneratedKeyType(final String keysClassName, final Class<? extends Keyed> apiType, final String pkg, final ResourceKey<? extends Registry<T>> registryKey) {
        this.keysClassName = keysClassName;
        this.apiType = apiType;
        this.pkg = pkg;
        this.registryKey = registryKey;
    }

    protected TypeSpec createTypeSpec() {
        final AnnotationSpec notNull = AnnotationSpec.builder(NotNull.class).build();
        final TypeName typedKey = ParameterizedTypeName.get(TypedKey.class, this.apiType);

        final TypeName keyType = TypeName.get(Key.class)
            .annotated(notNull);

        final MethodSpec.Builder create = MethodSpec.methodBuilder("create")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .addParameter(ParameterSpec.builder(keyType, "key", Modifier.FINAL)
                .build()
            )
            .addCode("return $T.create(key/*, <insert registry key here from reg mod API> */);", TypedKey.class)
            .returns(typedKey.annotated(notNull));

        final TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(this.keysClassName)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addJavadoc("Vanilla keys for {@link $T}.\n", this.apiType)
            .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "unused")
                .addMember("value", "$S", "SpellCheckingInspection")
                .build()
            )
            .addAnnotation(AnnotationSpec.builder(ClassName.get("io.papermc.paper.generated", "GeneratedFrom"))
                .addMember("value", "$S", SharedConstants.getCurrentVersion().getName())
                .build()
            )
            .addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build()
            );

        final Registry<T> registry = Main.REGISTRY_ACCESS.registryOrThrow(this.registryKey);
        final List<ResourceKey<T>> experimental = this.collectExperimentalKeys(registry);

        boolean allExperimental = true;
        for (final T value : registry) {
            final ResourceKey<T> key = registry.getResourceKey(value).orElseThrow();
            final String keyPath = key.location().getPath();
            final String fieldName = keyPath.toUpperCase(Locale.ENGLISH);
            final FieldSpec.Builder fieldBuilder = FieldSpec.builder(typedKey, fieldName, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$N(key($S))", create.build(), keyPath)
                .addJavadoc("{@link $T} key: {@code $L}", this.apiType, key.location().toString());
            if (experimental.contains(key)) {
                fieldBuilder.addAnnotations(EXPERIMENTAL_ANNOTATIONS);
            } else {
                allExperimental = false;
            }
            typeBuilder.addField(fieldBuilder.build());
        }
        if (allExperimental) {
            typeBuilder.addAnnotations(EXPERIMENTAL_ANNOTATIONS);
            create.addAnnotations(EXPERIMENTAL_ANNOTATIONS);
        }
        return typeBuilder.addMethod(create.build()).build();
    }

    @SuppressWarnings("unchecked")
    private List<ResourceKey<T>> collectExperimentalKeys(final Registry<T> registry) {
        final RegistrySetBuilder.@Nullable RegistryBootstrap<T> registryBootstrap = (RegistrySetBuilder.RegistryBootstrap<T>) EXPERIMENTAL_REGISTRY_ENTRIES.get(this.registryKey);
        if (registryBootstrap == null) {
            return Collections.emptyList();
        }
        final List<ResourceKey<T>> experimental = new ArrayList<>();
        final CollectingContext<T> context = new CollectingContext<>(experimental, registry);
        registryBootstrap.run(context);
        return experimental;
    }

    protected JavaFile createFile() {
        return JavaFile.builder(this.pkg, this.createTypeSpec())
            .skipJavaLangImports(true)
            .addStaticImport(Key.class, "key")
            .indent("    ")
            .build();
    }

    @Override
    public final String outputString() {
        return this.createFile().toString();
    }

    @Override
    public void writeToFile(final Path parent) throws IOException {
        final Path pkgDir = parent.resolve(this.pkg.replace('.', '/'));
        Files.createDirectories(pkgDir);
        Files.writeString(pkgDir.resolve(this.keysClassName + ".java"), this.outputString(), StandardCharsets.UTF_8);
    }
}
