import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.5.21"
    id("maven-publish")
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}

val VERSION_MAJOR = 1
val VERSION_MINOR = 0
val VERSION_PATCH = 4

group = "com.frogobox"
version = "$VERSION_MAJOR.$VERSION_MINOR.$VERSION_PATCH"

publishing {

    publications {
        register("gprRelease", MavenPublication::class) {
            groupId = project.group.toString()
            artifactId = rootProject.name
            version = project.version.toString()
            from(components["java"])
        }

        repositories {
            maven { url = uri("https://jitpack.io") }
        }

    }
}
