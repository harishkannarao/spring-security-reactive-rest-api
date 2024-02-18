plugins {
	java
	id("java-test-fixtures")
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.harishkannarao"
version = ""
java.sourceCompatibility = JavaVersion.VERSION_21

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
	testFixturesImplementation("io.projectreactor:reactor-test")
	testFixturesImplementation("org.springframework.security:spring-security-test")
	testFixturesImplementation("org.assertj:assertj-core")
}

testing {
	suites {
		val test by getting(JvmTestSuite::class) {
			dependencies {
				implementation("org.springframework.boot:spring-boot-starter-test")
				implementation("io.projectreactor:reactor-test")
				implementation("org.springframework.security:spring-security-test")
				implementation("org.assertj:assertj-core")
			}
		}
		val integrationTest by registering(JvmTestSuite::class) {
			dependencies {
				implementation(project())
				implementation(testFixtures(project()))
				implementation("org.springframework.boot:spring-boot-starter-security")

				implementation("org.springframework.boot:spring-boot-starter-test")
				implementation("io.projectreactor:reactor-test")
				implementation("org.springframework.security:spring-security-test")
				implementation("org.assertj:assertj-core")
			}
			targets {
				all {
					testTask.configure {
						mustRunAfter(test)
					}
				}
			}
		}
		val ftIntegrationTest by registering(JvmTestSuite::class) {
			dependencies {
				implementation(project())
				implementation(testFixtures(project()))
				implementation("org.springframework.boot:spring-boot-starter-security")

				implementation("org.springframework.boot:spring-boot-starter-test")
				implementation("io.projectreactor:reactor-test")
				implementation("org.springframework.security:spring-security-test")
				implementation("org.assertj:assertj-core")
			}
			targets {
				all {
					testTask.configure {
						mustRunAfter(integrationTest)
					}
				}
			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()

	testLogging.events = setOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED)

	val properties = System.getProperties().entries.associate { it.key.toString() to it.value }
	systemProperties(properties)
}

tasks.named("check") {
	dependsOn(testing.suites.named("integrationTest"), testing.suites.named("ftIntegrationTest"))
}
