plugins {
    kotlin("jvm") version "2.0.21"
    application
}

repositories { mavenCentral() }

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.4.1")
    implementation("io.ktor:ktor-client-core:3.0.1")
    implementation("io.ktor:ktor-client-cio:3.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

kotlin { jvmToolchain(21) }
kotlin.sourceSets.named("main") { kotlin.srcDir("../../kotlin") }
application { mainClass.set("HttpContractsKt") }
layout.buildDirectory.set(file(System.getenv("HTTP_SDK_JVM_BUILD") ?: "${System.getProperty("java.io.tmpdir")}/w21-http-sdk-jvm-build"))
dependencyLocking { lockAllConfigurations() }
