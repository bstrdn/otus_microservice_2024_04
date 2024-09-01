import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    idea
    java
    id("org.springframework.boot") version "3.3.0" apply true
    id("io.spring.dependency-management") version "1.1.5"
    id("com.google.cloud.tools.jib") version "3.4.3"
    id("org.openapi.generator") version "7.5.0"
    id("io.freefair.lombok") version "8.6"
}

version = "0.0.1-SNAPSHOT"
val jacksonDataVersion = "2.17.1"

idea {
    project {
        languageLevel = IdeaLanguageLevel(21)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

repositories {
    mavenCentral()
}

val generatedProjectDir = "$projectDir/build/generated"
val controllerDir = "ru.bstrdn.controller"
val modelDir = "ru.bstrdn.data.dto"
val openapiJson = "$projectDir/src/main/resources/openapi.yml"

tasks.named<GenerateTask>("openApiGenerate") {

    generatorName.set("spring")
    inputSpec.set(openapiJson)
    outputDir.set(generatedProjectDir)
    apiPackage.set(controllerDir)
    modelPackage.set(modelDir)

    configOptions.putAll(
        mapOf(
            Pair("dateLibrary", "java8-localdatetime"),
            Pair("useSpringBoot3", "true"),
            Pair("documentationProvider", "springdoc"),
            Pair("interfaceOnly", "true"),
            Pair("useTags", "true"),
            Pair("hideGenerationTimestamp", "true"),
            Pair("additionalModelTypeAnnotations",
                "@lombok.Data @lombok.NoArgsConstructor @lombok.AllArgsConstructor @lombok.experimental.SuperBuilder"
            )
        )
    )

    doFirst {
        delete(generatedProjectDir)
    }
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonDataVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonDataVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonDataVersion")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    //db
    implementation("org.postgresql:postgresql:42.7.3")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    //mapstruct \ lombok
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

jib {
    container {
        creationTime.set("USE_CURRENT_TIMESTAMP")
        jvmFlags = listOf("-Dspring.profiles.active=dev")
    }
    from {
        image = "bellsoft/liberica-openjdk-alpine:21-x86_64"
    }
    to {
        image = "bstrdn/otus_microservice"
        tags = setOf(project.version.toString())
        auth {
            username = System.getenv("GITLAB_USERNAME")
            password = System.getenv("GITLAB_PASSWORD")
        }
    }
}

afterEvaluate {
    extensions.configure<SourceSetContainer>("sourceSets") {
        named("main") {
            java {
                setSrcDirs(listOf("src/main/java", "build/generated/src/main/java"))
            }
            resources {
                setSrcDirs(listOf("src/main/resources", "build/generated/src/main/resources"))
            }
        }
    }
}