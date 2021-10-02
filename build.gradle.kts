val logbackVersion: String by project
val ktorVersion: String by project
val kotlinVersion: String by project
val kmongoVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.0"
}


group = "com.fruitpunchsamurai.myacademy"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.jetty.EngineMain"
}

repositories {
    mavenLocal()
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("io.ktor:ktor-server-jetty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-jetty:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")

    implementation("org.litote.kmongo:kmongo-async:$kmongoVersion")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kmongoVersion")
    implementation("org.litote.kmongo:kmongo-id-serialization:$kmongoVersion")

    implementation("org.kodein.di:kodein-di:7.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.31")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
