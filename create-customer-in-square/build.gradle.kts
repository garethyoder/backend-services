plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.cedarmeadowmeats.order-workflow.create-customer-in-square"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.1"
extra["awsSdkBom"] = "2.26.25"
extra["awsLambdaJavaCore"] = "1.2.3"
extra["awsLambdaJavaEvents"] = "3.11.4"
extra["awsLambdaJavaSerialization"] = "1.1.5"
extra["awsLambdaJavaTests"] = "1.1.1"

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-function-web")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws")
    implementation("com.amazonaws:aws-lambda-java-core:${property("awsLambdaJavaCore")}")
    implementation("com.amazonaws:aws-lambda-java-events:${property("awsLambdaJavaEvents")}")
    implementation("com.amazonaws:aws-lambda-java-serialization:${property("awsLambdaJavaSerialization")}")
    implementation("com.squareup:square:38.1.0.20240320")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.amazonaws:aws-lambda-java-tests:${property("awsLambdaJavaTests")}")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
