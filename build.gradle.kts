import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.compose") version "1.0.0-alpha3"
    id("maven-publish")
}

/** Artifact groupId. */
group = "com.frogobox"

/** Artifact version. Note that "SNAPSHOT" in the version is not supported by bintray. */
version = "1.0.3"

/** This is from settings.gradle.kts. */
val myArtifactId: String = rootProject.name

/** This is defined above as `group`. */
val myArtifactGroup: String = project.group.toString()

/** This is defined above as `version`. */
val myArtifactVersion: String = project.version.toString()

/** My GitHub username. */
val myGithubUsername = "amirisback"
val myGithubDescription = "Repository Sample For Native Kotlin Library"
val myGithubHttpUrl = "https://github.com/${myGithubUsername}/${myArtifactId}"
val myGithubIssueTrackerUrl = "https://github.com/${myGithubUsername}/${myArtifactId}/issues"
val myLicense = "Apache-2.0"
val myLicenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"

val myDeveloperName = "Muhammad Faisal Amir"


repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(compose.desktop.currentOs)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = myArtifactId
            packageVersion = myArtifactVersion
        }
    }
}


val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
    from("LICENCE.md") {
        into("META-INF")
    }
}

// More info on `publishing`:
//   https://docs.gradle.org/current/userguide/publishing_maven.html#publishing_maven:resolved_dependencies
// More info on authenticating with personal access token (myDeveloperId and myArtifactName must be lowercase):
//   https://docs.github.com/en/packages/guides/configuring-gradle-for-use-with-github-packages#authenticating-to-github-packages
publishing {

    publications {
        register("gprRelease", MavenPublication::class) {
            groupId = myArtifactGroup
            artifactId = myArtifactId
            version = myArtifactVersion

            from(components["java"])

            artifact(sourcesJar)

            pom {
                packaging = "jar"
                name.set(myArtifactId)
                description.set(myGithubDescription)
                url.set(myGithubHttpUrl)
                scm {
                    url.set(myGithubHttpUrl)
                }
                issueManagement {
                    url.set(myGithubIssueTrackerUrl)
                }
                licenses {
                    license {
                        name.set(myLicense)
                        url.set(myLicenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(myGithubUsername)
                        name.set(myDeveloperName)
                    }
                }
            }

        }
    }

    repositories {

        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${myGithubUsername}/${myArtifactId}")
            credentials {
                username = System.getenv("GITHUB_PACKAGES_USERID")
                password = System.getenv("GITHUB_PACKAGES_PUBLISH_TOKEN")
            }
        }

        maven { url = uri("https://jitpack.io") }

    }

}