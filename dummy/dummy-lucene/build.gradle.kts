plugins {
    id("java")
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.lucene:lucene-core:9.6.0")
    implementation("org.apache.lucene:lucene-queryparser:9.6.0")
    implementation("org.apache.lucene:lucene-analyzers-common:8.11.2")
    implementation("org.apache.lucene:lucene-analyzers-opennlp:8.11.2")
    runtimeOnly("org.apache.lucene:lucene-luke:9.7.0") // ui/x for inspecting needs
    // https://github.com/DmitryKey/luke/blob/master/luke.bat

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}