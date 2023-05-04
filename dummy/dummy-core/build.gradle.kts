plugins {
    java
}

group = "org.pocs"
version = "1.0.0"

repositories {
    mavenCentral()
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.pocs.Emo"
    }
}

dependencies {
    implementation("org.apache.httpcomponents:httpclient:4.5.4")
    implementation("com.sun.mail:javax.mail:1.6.2")
    implementation("org.seleniumhq.selenium:selenium-java:3.141.59")
    implementation("commons-beanutils:commons-beanutils:1.9.4")
    implementation("org.postgresql:postgresql:42.2.23")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")


    implementation("io.github.resilience4j:resilience4j-bulkhead:2.0.2")
    compileOnly("org.projectlombok:lombok:1.18.26")

//    testCompile("junit", "junit", "4.12")
}


