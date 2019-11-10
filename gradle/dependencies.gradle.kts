val implementation by configurations
val testImplementation by configurations

val integrationTestImplementation by configurations.creating {
    extendsFrom(testImplementation)
}

val kotlinVersion: String by extra
val junitVersion: String by extra
val jacksonVersion: String by extra
val springDataVersion : String by extra
val springBootVersion: String by extra
val springVersion: String by extra
val dokkaVersion : String by extra
val nucleusVersion: String by extra

data class Package(
    val groupId: String,
    val artifactId: String,
    val version: String
)


val nucleusPackages: Array<Package> = arrayOf(
    Package("com.immanuelqrw.core", "nucleus-util", nucleusVersion),
    Package("com.immanuelqrw.core", "nucleus-test", nucleusVersion),
    Package("com.immanuelqrw.core", "nucleus-entity", "0.0.15-pre-alpha"),
    Package("com.immanuelqrw.core", "nucleus-api", "0.0.26-pre-alpha")
)

val nucleusTestPackages: Array<Package> = arrayOf(
)

val jacksonPackages: Array<Package> = arrayOf(
    Package("com.fasterxml.jackson.module", "jackson-module-kotlin", jacksonVersion),
    Package("com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml", jacksonVersion),
    Package( "com.fasterxml.jackson.datatype", "jackson-datatype-jsr310", jacksonVersion)
)

val springPackages: Array<Package> = arrayOf(
    Package("org.springframework.data", "spring-data-jpa", springDataVersion),
    Package("org.springframework", "spring-orm", springVersion),
    Package("org.springframework", "spring-web", springVersion),
    Package("org.springframework", "spring-webmvc", springVersion)
)

val springBootPackages: Array<Package> = arrayOf(
    Package("org.springframework.boot", "spring-boot-autoconfigure", springBootVersion),
    Package("org.springframework.boot", "spring-boot", springBootVersion),
    Package("org.springframework.boot", "spring-boot-starter-tomcat", springBootVersion),
    Package("org.springframework.boot", "spring-boot-gradle-plugin", springBootVersion)
)

val databasePackages: Array<Package> = arrayOf(
    Package("org.postgresql", "postgresql", "42.2.5"),
    Package("com.h2database", "h2", "1.4.199"),
    Package("org.hibernate", "hibernate-core", "5.4.4.Final"),
    Package("org.hibernate.validator", "hibernate-validator", "6.0.17.Final")
)

val jUnitPackages: Array<Package> = arrayOf(
    Package("org.junit.jupiter", "junit-jupiter-api", junitVersion),
    Package("org.junit.jupiter", "junit-jupiter-params", junitVersion),
    Package("org.junit.jupiter", "junit-jupiter-engine", junitVersion),
    Package("org.junit.platform", "junit-platform-commons", "1.5.2"),
    Package("org.junit.platform", "junit-platform-launcher", "1.5.2"),

    Package("io.mockk", "mockk", "1.8.13"),
    Package("org.amshove.kluent", "kluent", "1.49")
)

val springTestPackages: Array<Package> = arrayOf(
    Package("org.springframework", "spring-test", springVersion),
    Package("org.springframework.boot", "spring-boot-test", springBootVersion),
    Package("org.springframework.boot", "spring-boot-test-autoconfigure", springBootVersion)
)

val packages: Array<Package> = arrayOf(
    Package("org.jetbrains.kotlin", "kotlin-reflect", kotlinVersion),
    Package("org.jetbrains.dokka", "dokka-gradle-plugin", dokkaVersion),
    *nucleusPackages,
    *jacksonPackages,
    *springPackages,
    *springBootPackages,
    *springTestPackages,
    *databasePackages,
    *jUnitPackages,
    Package( "com.google.guava", "guava", "28.1-jre"),
    Package("io.swagger.core.v3", "swagger-jaxrs2", "2.0.0"),
    Package("io.swagger.core.v3", "swagger-jaxrs2-servlet-initializer", "2.0.0"),
    Package("org.springdoc", "springdoc-openapi-ui", "1.1.43"),
    Package("org.springframework.security", "spring-security-config", springVersion),
    Package("org.springframework.security", "spring-security-core", springVersion),
    Package("org.springframework.security", "spring-security-web", springVersion)
)

val testPackages: Array<Package> = arrayOf(
    Package("org.hibernate", "hibernate-testing", "5.3.7.Final"),
    *jUnitPackages,
    *springTestPackages,
    *nucleusTestPackages,
    Package("org.springframework.security", "spring-security-test", springVersion)
)

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    packages.forEach { `package` ->
        implementation(`package`.groupId, `package`.artifactId, `package`.version)
    }

    testPackages.forEach { testPackage ->
        testImplementation(testPackage.groupId, testPackage.artifactId, testPackage.version)
    }
}
