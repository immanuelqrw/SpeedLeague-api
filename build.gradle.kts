import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val projectGroup = "com.immanuelqrw.speedleague"
val projectArtifact = "speedleague-api"
val projectVersion = "0.0.1-pre-alpha"

group = projectGroup
version = projectVersion

apply(from = "gradle/constants.gradle.kts")

plugins {
    java
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.noarg") version "1.3.50"
    kotlin("plugin.allopen") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    id("org.sonarqube") version "2.6"
    id("org.jetbrains.dokka") version "0.9.17"
    idea
    application
    `maven-publish`
}

application {
    mainClassName = "com.immanuelqrw.speedleague.api.ApplicationKt"
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "http://localhost:8081/repository/maven-public/")
}


apply(from = "gradle/dependencies.gradle.kts")

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    withType<Wrapper> {
        gradleVersion = "5.0"
    }

    withType<DokkaTask> {
        outputFormat = "html"
        outputDirectory = "$buildDir/docs/dokka"
    }
}

val databaseBuild by tasks.creating(Exec::class) {
    workingDir("./script")
    commandLine("python", "instantiate_database.py")
}

val testDatabaseBuild: Exec by tasks.creating(Exec::class) {
    workingDir("./script")
    commandLine("python", "construct_database.py")
}

sourceSets.create("integrationTest") {
    java.srcDir(file("src/integrationTest/java"))
    java.srcDir(file("src/integrationTest/kotlin"))
    resources.srcDir(file("src/integrationTest/resources"))
    compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
    runtimeClasspath += output + compileClasspath
}

val test: Test by tasks
val integrationTest by tasks.creating(Test::class) {
    description = "Runs the integration tests."
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    // dependsOn(testDatabaseBuild)
    mustRunAfter(test)
}


val sonarHostUrl: String by project
val sonarOrganization: String by project
val sonarLogin: String by project

sonarqube {
    properties {
        property("sonar.host.url", sonarHostUrl)
        property("sonar.organization", sonarOrganization)
        property("sonar.login", sonarLogin)

        property("sonar.projectKey", "immanuelqrw_SpeedLeague-API")
        property("sonar.projectName", "SpeedLeague-API")
        property("sonar.projectVersion", version)
    }
}
val sonar: Task = tasks["sonarqube"]

val check by tasks.getting {
    //    dependsOn(integrationTest)
//    dependsOn(sonar)
}


val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

val repoUsername: String by project
val repoPassword: String by project

publishing {
    repositories {
        maven {
            url = uri("http://localhost:8081/repository/maven-releases/")
            credentials {
                username = repoUsername
                password = repoPassword
            }
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            groupId = projectGroup
            artifactId = projectArtifact
            version = projectVersion
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}
