
pluginManagement {
    repositories {
        mavenLocal()
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven {  url = uri("https://maven.aliyun.com/repository/public") }
        maven {  url = uri("https://maven.aliyun.com/repository/google") }
        maven {  url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
        maven {  url = uri("https://maven.aliyun.com/repository/spring") }
        maven {  url = uri("https://maven.aliyun.com/repository/spring-plugin") }
        maven {  url = uri("https://maven.aliyun.com/repository/grails-core") }
        maven {  url = uri("https://maven.aliyun.com/repository/apache-snapshots") }
        maven {
            url = uri("https://mirrors.huaweicloud.com/repository/maven/")
        }
        maven { url = uri("https://repo.spring.io/libs-release") }
        mavenCentral()
    }
}

//https://docs.gradle.org/current/userguide/platforms.html
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("springBootVersion", "2.7.18")
            version("hutoolVersion", "5.8.22")
            version("mybatisPlusVersion", "3.5.4")
            version("mapStructVersion", "1.5.5.Final")
            version("thymeleafExtraVersion", "3.0.4.RELEASE")
            library("guava", "com.google.guava:guava:32.1.3-jre")
            library("mybatis-plus-boot-starter", "com.baomidou","mybatis-plus-boot-starter").versionRef("mybatisPlusVersion")
            library("mybatis-plus", "com.baomidou","mybatis-plus").versionRef("mybatisPlusVersion")
            library("mybatis-plus-test", "com.baomidou","mybatis-plus-boot-starter-test").versionRef("mybatisPlusVersion")
            library("hutool-bom", "cn.hutool","hutool-bom").versionRef("hutoolVersion")
            library("mapStruct", "org.mapstruct","mapstruct").versionRef("mapStructVersion")
            library("mapStructProcessor", "org.mapstruct","mapstruct-processor").versionRef("mapStructVersion")
        }
    }
}


rootProject.name = "my-boot-template"
include("template-common")
include("template-pojo")
include("template-web")
