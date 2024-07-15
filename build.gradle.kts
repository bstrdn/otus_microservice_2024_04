plugins {
    idea
    java
    id("org.springframework.boot") version "3.3.0" apply true
    id("io.spring.dependency-management") version "1.1.5"
    id("com.google.cloud.tools.jib") version "3.4.3"
}

version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
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