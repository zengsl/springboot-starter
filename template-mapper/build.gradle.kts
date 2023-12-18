dependencies {
    api(project(":template-pojo"))
    implementation(libs.mybatis.plus.test)
    implementation(libs.mybatis.plus.boot.starter)
    implementation("com.mysql:mysql-connector-j")
}