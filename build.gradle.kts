plugins {
    kotlin("jvm") version "1.4.10"
    id("kr.entree.spigradle") version "2.2.3"
}

group = "kr.sul"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io")
    mavenLocal()
}

val pluginStorage = "C:/Users/PHR/Desktop/PluginStorage"
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("com.destroystokyo.paper", "paper-api", "1.12.2-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc", "spigot", "1.12.2-R0.1-SNAPSHOT")

    compileOnly(files("$pluginStorage/Dependencies/GlowAPI_v1.4.6_S.jar"))
    compileOnly(files("$pluginStorage/Dependencies/Tabbed.jar"))

    testImplementation("com.github.seeseemelk", "MockBukkit-v1.13-spigot", "0.2.0")
}

spigot {
    authors = listOf("SuL")
    apiVersion = "1.12"
    version = project.version.toString()
    softDepends = listOf("GlowAPI")
    commands {
        create("파티") {
            permission = "op.op"
        }
    }
}


tasks {
    compileJava.get().options.encoding = "UTF-8"
    compileKotlin.get().kotlinOptions.jvmTarget = "1.8"
    compileTestKotlin.get().kotlinOptions.jvmTarget = "1.8"

    val copyPlugin = register<Copy>("copyPlugin") {
        from(files("$pluginStorage/${project.name}_S.jar"))
        into(file("C:/Users/PHR/Desktop/SERVER2/plugins"))
    }

    jar {
        archiveFileName.set("${project.name}_S.jar")
        destinationDirectory.set(file(pluginStorage))

        finalizedBy(copyPlugin)
    }
}
