import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java
    idea
    eclipse
    jacoco
    // Far better annotation processor (e.g. Lombok) integration in Intellij
    id("net.ltgt.apt") version "0.21"
    id("net.ltgt.apt-idea") version "0.21"
}

group = "com.github.Sir4ur0n"
version = "1.0"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    jcenter()
}

dependencies {
    // JUnit testing API
    val junitJupiterVersion = "5.3.2"
    // Generate boilerplate getter, setter and more
    val lombokVersion = "1.18.4"
    // Functional control and immutable data types for Java 8
    val vavrVersion = "0.10.0"
    // Mocking framework
    val mockitoVersion = "2.23.4"
    // Assertion framework, much more fluent than default JUnit assertions
    val assertjVersion = "3.11.1"
    val assertjVavrVersion = "0.1.0"
    // Property based testing (PBT)
    val junitQuickcheckVersion = "0.9"
    val junitQuickcheckVavrVersion = "1.0"
    // Automatically add classes to service loader (useful for JUnit-QuickCheck generators)
    val autoServiceVersion = "1.0-rc4"
    // Stop spamming when I run my tests, SLF4J, please, pretty please
    val slf4jVersion = "1.7.25"

    implementation("io.vavr", "vavr", vavrVersion)
    implementation("org.projectlombok", "lombok", lombokVersion)
    implementation("org.slf4j", "slf4j-nop", slf4jVersion)

    annotationProcessor("org.projectlombok", "lombok", lombokVersion)

    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitJupiterVersion)
    testImplementation("org.junit.jupiter", "junit-jupiter-params", junitJupiterVersion)
    testImplementation("org.mockito", "mockito-core", mockitoVersion)
    testImplementation("org.mockito", "mockito-junit-jupiter", mockitoVersion)
    testImplementation("org.assertj", "assertj-core", assertjVersion)
    testImplementation("org.assertj", "assertj-vavr", assertjVavrVersion)
    testImplementation("com.pholser", "junit-quickcheck-core", junitQuickcheckVersion)
    testImplementation("com.pholser", "junit-quickcheck-generators", junitQuickcheckVersion)
    testImplementation("com.github.sir4ur0n", "junit-quickcheck-vavr", junitQuickcheckVavrVersion)
    testImplementation("com.google.auto.service", "auto-service", autoServiceVersion)

    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitJupiterVersion)
    testRuntimeOnly("org.junit.vintage", "junit-vintage-engine", junitJupiterVersion)

    testAnnotationProcessor("org.projectlombok", "lombok", lombokVersion)
    testAnnotationProcessor("com.google.auto.service", "auto-service", autoServiceVersion)
}

/**
 * Configure all tasks of type Test to use JUnit 5 platform, and test logging
 */
tasks.withType(Test::class) {
    // Register the JUnit 5
    useJUnitPlatform()
    // Fine tune how tests logs in the console
    testLogging {
        displayGranularity = 2
        events(TestLogEvent.FAILED, TestLogEvent.SKIPPED, TestLogEvent.STANDARD_ERROR)
        showExceptions = true
        showCauses = true
        exceptionFormat = TestExceptionFormat.FULL
    }
}
