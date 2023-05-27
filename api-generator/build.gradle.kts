import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform

plugins {
    java
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

minecraft {
    version("1.19.4")
    platform(MinecraftPlatform.SERVER)

    runs {
        server("generate") {
            mainClass("io.papermc.generator.Main")
        }
    }
}

dependencies {
    implementation("com.squareup:javapoet:1.13.0")
    implementation("net.kyori:adventure-key:4.13.1")
}

group = "io.papermc.paper"
version = "1.0-SNAPSHOT"

