import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

group = rootProject.group
version = rootProject.version

dependencies {
    api(project(":schema"))
    api(project(":dsl:kotless:kotless-lang"))
    api(project(":dsl:common:dsl-parser-common"))
}


