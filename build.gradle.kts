import com.airthings.buildtools.util.Dependencies
import com.airthings.buildtools.util.Library
import com.airthings.buildtools.util.testImplementationAll

group = "com.airthings.cloud.kotless"

plugins {
    `java-library`
    `maven-publish`
}

kotlin {
    explicitApi()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementationAll(*Dependencies.kotest)
    testImplementationAll(*Dependencies.moshi)
    testImplementation(Dependencies.kotlinsnapshot)
    implementation(Dependencies.sensortype)
}

val latestTag = command("git describe --tags --abbrev=0") ?: "dummy.version" // dummy version for builds != deployment/publish

subprojects {
    version = latestTag
}

publishing {
    repositories {
        maven {
            name = "AirthingsGitHubPackages"
            url = uri("https://maven.pkg.github.com/airthings/lib-kotless")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        withType<MavenPublication> {
            pom {
                artifactId = "kotless"
            }
        }
    }
}

fun command(command: String): String? {
    val cmd = command.split(" ").toTypedArray()
    val process = ProcessBuilder(*cmd)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .start()
    return process.inputStream.bufferedReader().readLine()?.trim()
}
