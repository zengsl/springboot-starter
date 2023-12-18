/*
    buildscript代码块中的声明是gradle脚本自身需要的资源。
    可以声明的资源包括依赖项、第三方插件、maven仓库地址等。
    gradle在执行脚本时，会优先执行buildscript代码块中的内容，然后才会执行剩余的build脚本。
    该部分代码应该置顶
*/
buildscript {

}

plugins {
    idea
    java
    id("org.springframework.boot") version "2.7.18"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.compileJava {
    options.release = 11
}

/*应用自定义的扩展信息文件 */
//apply("version.gradle")

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")

    dependencies {
/*        implementation(platform("org.springframework.boot:spring-boot-dependencies:2.7.18"))*/

        // 从groovy中转为kotlin，在groovy中可以直接使用libs 在kotlin中要使用rootProject.libs 参考： https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:interoperability
        implementation(platform(rootProject.libs.hutool.bom))
        implementation(rootProject.libs.guava)
        compileOnly("org.projectlombok:lombok")
        testCompileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        //*lombok要在spring-boot-configuration-processor之前执行*//
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

allprojects {
    group = "com.ddp.res"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java")

    repositories {
        mavenLocal()
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven { url = uri("https://maven.aliyun.com/repository/google") }
    }

}

/*指定项目配置*/
/*
project("") {

}*/
