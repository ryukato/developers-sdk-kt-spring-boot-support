@file:Suppress("UnstableApiUsage")

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Versions {
    const val kotlin = "1.4.0"
    const val coroutines = "1.3.9"
    const val coroutinesCoreJs = "1.3.3"
    const val ktor = "1.3.0"
    const val apacheCommon = "3.10"
    const val commonCodec = "1.14"
}

object Libs {
    // kotlin
    const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val kotlinxCoroutinesCoreJs = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${Versions.coroutinesCoreJs}"

    // ktor
    const val ktorClient = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorClientCio = "io.ktor:ktor-client-cio:${Versions.ktor}"
    const val ktorClientJackson = "io.ktor:ktor-client-jackson:${Versions.ktor}"
    const val ktorClientLoggingJvm = "io.ktor:ktor-client-logging-jvm:${Versions.ktor}"
    // ktor-test
    const val ktorClientMock = "io.ktor:ktor-client-mock:${Versions.ktor}"
    const val ktorClientMockJvm = "io.ktor:ktor-client-mock-jvm:${Versions.ktor}"
    const val ktorClientMockJs = "io.ktor:ktor-client-mock-js:${Versions.ktor}"
    const val ktorClientMockNative = "io.ktor:ktor-client-mock-native:${Versions.ktor}"

    //apache-common
    const val commonsLang = "org.apache.commons:commons-lang3:${Versions.apacheCommon}"
    const val commonCodec = "commons-codec:commons-codec:${Versions.commonCodec}"
}

fun Project.ktorDependencies() {
    dependencies {
        "implementation"(Libs.ktorClient)
        "implementation"(Libs.ktorClientCio)
        "implementation"(Libs.ktorClientJackson)
        "implementation"(Libs.ktorClientLoggingJvm)
        "implementation"(Libs.commonsLang)
        "implementation"(Libs.commonCodec)
        "testImplementation"(Libs.ktorClientMock)
        "testImplementation"(Libs.ktorClientMockJvm)
        "testImplementation"(Libs.ktorClientMockJs)
        "testImplementation"(Libs.ktorClientMockNative)
    }
}

fun Project.kotlinCoroutineDependencies(requiresReactor: Boolean = true) {
    dependencies {
        "implementation"(Libs.kotlinxCoroutinesCore)
        "implementation"(Libs.kotlinxCoroutinesCoreJs)
        "testImplementation"("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")

    }
}
