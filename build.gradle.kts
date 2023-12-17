/*
    buildscript代码块中的声明是gradle脚本自身需要的资源。
    可以声明的资源包括依赖项、第三方插件、maven仓库地址等。
    gradle在执行脚本时，会优先执行buildscript代码块中的内容，然后才会执行剩余的build脚本。
    该部分代码应该置顶
*/
buildscript {

}

plugins {
    java
    id("org.springframework.boot") version "2.7.18"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

/*应用自定义的扩展信息文件 */
// 这里也规避了https://github.com/gradle/gradle/issues/18237 提到的Bug，在allprojects、subprojects上方引入扩展信息
apply("version.gradle")


subprojects {

    apply(plugin = "java-library")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")

    dependencies {
/*        implementation(platform("org.springframework.boot:spring-boot-dependencies:2.7.18"))*/
        implementation(platform(libs.hutool.bom))
        implementation(libs.guava)
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
