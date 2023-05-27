package io.papermc.generator.types;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import net.kyori.adventure.key.Key;
import net.minecraft.SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

public class GeneratedKeyType implements SourceGenerator {

    private final String keysClassName;
    private final String typeName;
    private final String pkg;
    private final Registry<?> registry;

    public GeneratedKeyType(final String keysClassName, final String typeName, final String pkg, final Registry<?> registry) {
        this.keysClassName = keysClassName;
        this.typeName = typeName;
        this.pkg = pkg;
        this.registry = registry;
    }

    protected TypeSpec createTypeSpec() {
        final TypeSpec.Builder builder = TypeSpec.classBuilder(this.keysClassName)
            .addModifiers(PUBLIC, FINAL)
            .addJavadoc("Vanilla %s keys\n".formatted(this.typeName))
            .addAnnotation(AnnotationSpec.builder(ClassName.get("io.papermc.paper.generated", "GeneratedFrom"))
                .addMember("value", "$S", SharedConstants.getCurrentVersion().getName())
                .build()
            ).addMethod(MethodSpec.constructorBuilder()
                .addModifiers(PRIVATE)
                .build()
            );

        for (final ResourceLocation key : this.registry.keySet()) {
            final String fieldName = key.getPath().toUpperCase(Locale.ENGLISH);
            builder.addField(FieldSpec.builder(Key.class, fieldName, PUBLIC, STATIC, FINAL)
                .initializer("Key.key($S)", key.getPath())
                .addJavadoc("%s key: {@code %s}".formatted(this.typeName, key.toString()))
                .build()
            );
        }
        return builder.build();
    }

    protected JavaFile createFile() {
        return JavaFile.builder(this.pkg, this.createTypeSpec())
            .build();
    }

    @Override
    public final String outputString() {
        return this.createFile().toString();
    }

    @Override
    public void writeToFile(final Path parent) throws IOException {
        Files.writeString(parent.resolve(this.keysClassName + ".java"), this.outputString(), StandardCharsets.UTF_8);
    }
}
