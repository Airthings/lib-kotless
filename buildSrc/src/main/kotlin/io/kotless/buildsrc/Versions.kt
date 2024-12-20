package io.kotless.buildsrc

object Versions {
    //Note, that it also should be changed in dependencies of buildSrc and in plugins blocks
    //Due to limitations of Gradle DSL
    const val kotlin = "1.5.31"
    const val serialization = "1.0.1"

    const val aws = "1.11.788"
    const val lambdaJavaCore = "1.2.0"

    const val slf4j = "1.7.30"
    const val logback = "1.2.3"

    const val serverlessContainers = "1.5.2"

    const val ktor = "2.2.1"

    const val springBoot = "2.4.2"
    const val spring = "5.3.3"

    const val quartz = "2.3.2"
}
