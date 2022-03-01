plugins {
    java
    kotlin("jvm") version "1.5.21"
    `maven-publish`
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

val VERSION_MAJOR = 1
val VERSION_MINOR = 0
val VERSION_PATCH = 5

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
