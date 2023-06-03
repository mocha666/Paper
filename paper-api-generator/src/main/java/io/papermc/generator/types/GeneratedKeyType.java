package io.papermc.generator.types;

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
import java.util.Locale;
import javax.lang.model.element.Modifier;
import net.kyori.adventure.key.Key;
import net.minecraft.SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.GameEvent;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;

public class GeneratedKeyType implements SourceGenerator {

    private final String keysClassName;
    private final String typeName;
    private final String pkg;
    private final ResourceKey<? extends Registry<?>> registryKey;

    public GeneratedKeyType(final String keysClassName, final String typeName, final String pkg, final ResourceKey<? extends Registry<?>> registryKey) {
        this.keysClassName = keysClassName;
        this.typeName = typeName;
        this.pkg = pkg;
        this.registryKey = registryKey;
    }

    protected TypeSpec createTypeSpec() {
        final AnnotationSpec notNull = AnnotationSpec.builder(NotNull.class).build();
        final TypeName typedKey = ParameterizedTypeName.get(TypedKey.class, GameEvent.class);

        final TypeName keyType = TypeName.get(Key.class)
            .annotated(notNull);

        final MethodSpec create = MethodSpec.methodBuilder("create")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .addParameter(ParameterSpec.builder(keyType, "key", Modifier.FINAL)
                .build()
            )
            .addCode("return $T.create(key/*, RegistryKey.GAME_EVENT */);", TypedKey.class)
            .returns(typedKey.annotated(notNull))
            .build();

        final TypeSpec.Builder builder = TypeSpec.classBuilder(this.keysClassName)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addJavadoc("Vanilla $L keys\n", this.typeName)
            .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "unused")
                .build()
            )
            .addAnnotation(AnnotationSpec.builder(ClassName.get("io.papermc.paper.generated", "GeneratedFrom"))
                .addMember("value", "$S", SharedConstants.getCurrentVersion().getName())
                .build()
            )
            .addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build()
            )
            .addMethod(create);

        for (final ResourceLocation key : Main.REGISTRY_ACCESS.registryOrThrow(this.registryKey).keySet()) {
            final String fieldName = key.getPath().toUpperCase(Locale.ENGLISH);
            builder.addField(FieldSpec.builder(typedKey, fieldName, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$N(key($S))", create, key.getPath())
                .addJavadoc("$L key: {@code $L}", this.typeName, key.toString())
                .build()
            );
        }
        return builder.build();
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
