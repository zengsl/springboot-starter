dependencies {
    implementation(libs.jackson.databind)
    implementation(libs.jackson.modules.java8)
    implementation(libs.jackson.module.parameter)
    implementation(libs.jackson.datatype.jsr310)
    implementation(libs.jackson.datatype.jdk8)
    implementation("ch.qos.logback:logback-classic")
    implementation(libs.commons.lang3)
    implementation(libs.commons.math3)
    implementation(libs.jsoup)
    implementation("cn.hutool:hutool-core")
}