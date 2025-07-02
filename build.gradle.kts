plugins {
    id("java")
    id("java-library")
    id("com.diffplug.spotless") version "7.0.4"
    id("com.gradleup.shadow") version "8.3.6"
    eclipse
}

group = "de.sprax2013"
version = "1.13-OG"

val pluginName = "BetterChairs"

allprojects {

val customMavenLocal = System.getProperty("SELF_MAVEN_LOCAL_REPO")
if (customMavenLocal != null) {
    val mavenLocalDir = file(customMavenLocal)
    if (mavenLocalDir.isDirectory) {
        repositories {
            maven {
                url = uri("file://${mavenLocalDir.absolutePath}")
            }
        }
    }
}
 
repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.codemc.io/repository/maven-public/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven("https://repo.sprax2013.de/repository/maven-snapshots/")
        maven("https://repo.sprax2013.de/repository/maven-releases/")
        maven("https://repo.purpurmc.org/snapshots")
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "com.gradleup.shadow")
    apply(plugin = "eclipse")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
            vendor = JvmVendorSpec.GRAAL_VM
        }
    }

    tasks.withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }

    tasks.withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-parameters")
        options.compilerArgs.add("-Xlint:deprecation")
        options.encoding = "UTF-8"
        options.isFork = true
    }

    spotless {
        java {
            removeUnusedImports()
            palantirJavaFormat()
        }
        kotlinGradle {
            ktfmt().kotlinlangStyle().configure { it.setMaxWidth(120) }
            target("build.gradle.kts", "settings.gradle.kts")
        }
    }

    tasks.named("spotlessCheck") {
        dependsOn("spotlessApply")
    }
}

project(":modules:betterchairs-api") {
    dependencies {
        compileOnly("org.spigotmc:spigot-api:1.8-R0.1-SNAPSHOT")
        api("de.sprax2013.lime:lime-spigot-api:0.0.4-SNAPSHOT")
        api("de.tr7zw:item-nbt-api:2.15.0")
        api("com.github.cryptomorin:XSeries:13.3.0")
        compileOnly("org.jetbrains:annotations:26.0.2")
    }
}

project(":modules:betterchairs-plugin") {
    val apiVersion = "1.8"

    tasks.named<ProcessResources>("processResources") {
        val props = mapOf(
            "version" to version,
            "apiVersion" to apiVersion,
            "pluginName" to pluginName
        )
        inputs.properties(props)
        filesMatching("plugin.yml") { expand(props) }
        from(rootProject.file("LICENSE")) { into("/") }
    }

    tasks.jar { archiveClassifier.set("part") }

	tasks.shadowJar {
		exclude("META-INF/**", "LICENSE")
		minimize()
		archiveClassifier.set("")
		archiveBaseName.set("BetterChairs")
		destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
		relocate("betterchairs.nms", "de.sprax2013.betterchairs.nms")
		relocate("de.sprax2013.lime", "de.sprax2013.betterchairs.third_party.de.sprax2013.lime")
		relocate("de.tr7zw.changeme.nbtapi", "de.sprax2013.betterchairs.third_party.de.tr7zw.nbtapi")
		relocate("com.cryptomorin.xseries", "de.sprax2013.betterchairs.third_party.com.cryptomorin.xseries")
	}

    tasks.build {
        dependsOn(tasks.spotlessApply)
        dependsOn(tasks.shadowJar)
    }

    dependencies {
        compileOnly("org.spigotmc:spigot-api:1.8-R0.1-SNAPSHOT")
        compileOnly("me.clip:placeholderapi:2.11.6")
        implementation(project(":modules:betterchairs-api"))
        listOf(
            "v1_19_R3",
            "v1_19_R2", "v1_19_R1", "v1_19_0", "v1_18_R2", "v1_18_R1",
            "v1_17_R1", "v1_16_R3", "v1_16_R2", "v1_16_R1", "v1_15_R1",
            "v1_14_R1", "v1_13_R2", "v1_13_R1", "v1_12_R1", "v1_11_R1",
            "v1_10_R1", "v1_9_R2", "v1_9_R1", "v1_8_R3", "v1_8_R2", "v1_8_R1"
        ).forEach {
            implementation(project(":modules:nms:betterchairs-$it"))
        }
        compileOnly("org.jetbrains:annotations:26.0.2")
    }
}

val nmsVersions = mapOf(
    "betterchairs-v1_8_R1"  to "1.8-R0.1-SNAPSHOT",
    "betterchairs-v1_8_R2"  to "1.8.3-R0.1-SNAPSHOT",
    "betterchairs-v1_8_R3"  to "1.8.8-R0.1-SNAPSHOT",
    "betterchairs-v1_9_R1"  to "1.9.2-R0.1-SNAPSHOT",
    "betterchairs-v1_9_R2"  to "1.9.4-R0.1-SNAPSHOT",
    "betterchairs-v1_10_R1" to "1.10.2-R0.1-SNAPSHOT",
    "betterchairs-v1_11_R1" to "1.11.2-R0.1-SNAPSHOT",
    "betterchairs-v1_12_R1" to "1.12.2-R0.1-SNAPSHOT",
    "betterchairs-v1_13_R1" to "1.13-R0.1-SNAPSHOT",
    "betterchairs-v1_13_R2" to "1.13.2-R0.1-SNAPSHOT",
    "betterchairs-v1_14_R1" to "1.14.4-R0.1-SNAPSHOT",
    "betterchairs-v1_15_R1" to "1.15.2-R0.1-SNAPSHOT",
    "betterchairs-v1_16_R1" to "1.16.1-R0.1-SNAPSHOT",
    "betterchairs-v1_16_R2" to "1.16.3-R0.1-SNAPSHOT",
    "betterchairs-v1_16_R3" to "1.16.5-R0.1-SNAPSHOT",
    "betterchairs-v1_17_R1" to "1.17-R0.1-SNAPSHOT",
    "betterchairs-v1_18_R1" to "1.18.1-R0.1-SNAPSHOT",
    "betterchairs-v1_18_R2" to "1.18.2-R0.1-SNAPSHOT",
    "betterchairs-v1_19_0"  to "1.19-R0.1-SNAPSHOT",
    "betterchairs-v1_19_R1" to "1.19.2-R0.1-SNAPSHOT",
    "betterchairs-v1_19_R2" to "1.19.3-R0.1-SNAPSHOT",
    "betterchairs-v1_19_R3" to "1.19.4-R0.1-SNAPSHOT",
)

project(":modules:nms:betterchairs-v1_19_R1") {
    dependencies {
        compileOnly(project(":modules:nms:betterchairs-v1_19_0"))
    }
}

subprojects
    .filter { it.path.startsWith(":modules:nms:") }
    .forEach { nms ->
        nms.dependencies {
            "compileOnly"(project(":modules:betterchairs-api"))
            val ver = nmsVersions.getValue(nms.name)
            "compileOnly"("org.spigotmc:spigot-api:$ver")
            val mojangMapped = ver.startsWith("1.19")
            if (mojangMapped) {
                "compileOnly"("org.spigotmc:spigot:$ver:remapped-mojang")
            } else {
                "compileOnly"("org.spigotmc:spigot:$ver")
            }
        }
        nms.tasks.withType<PublishToMavenRepository>().configureEach { enabled = false }
    }

