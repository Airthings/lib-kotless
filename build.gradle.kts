import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

group = "io.kotless"

plugins {
    id("io.gitlab.arturbosch.detekt") version ("1.15.0") apply true
    kotlin("jvm") version "1.8.0" apply false
    `maven-publish`
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("maven-publish")
        plugin("io.gitlab.arturbosch.detekt")
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven(url = uri("https://packages.jetbrains.team/maven/p/ktls/maven"))
    }

    val sourceSets = this.extensions.getByName("sourceSets") as SourceSetContainer


    task<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"]!!.allSource)
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
            register("libKotless", MavenPublication::class) {
                from(components["java"])
            }
        }
    }
    tasks.withType<KotlinJvmCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    detekt {
        parallel = true

        config = rootProject.files("detekt.yml")

        reports {
            xml {
                enabled = false
            }
            html {
                enabled = false
            }
        }
    }

    afterEvaluate {
        System.setProperty("gradle.publish.key", System.getenv("gradle_publish_key") ?: "")
        System.setProperty("gradle.publish.secret", System.getenv("gradle_publish_secret") ?: "")
    }
}
