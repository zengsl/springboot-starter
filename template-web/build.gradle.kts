plugins {
    // This plugin allows you to generate an OpenAPI 3 specification for a Spring Boot application from a Gradle build
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
}

dependencies {
    api(project(":template-service"))
    implementation("cn.hutool:hutool-core")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // https://springdoc.org/v1/
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}