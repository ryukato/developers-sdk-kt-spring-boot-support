@file:Suppress("UnstableApiUsage")
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	id("org.springframework.boot") version "2.4.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.31"
	kotlin("plugin.spring") version "1.4.31"
	`maven-publish`
	signing
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

buildscript {
	repositories {
		mavenLocal()
		mavenCentral()
	}
}

group = projectGroupId
version = "0.0.2"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

ktorDependencies()
kotlinCoroutineDependencies()

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("com.github.ryukato:link-developers-sdk-kt:0.0.2")
	// kotlin logging
	implementation("io.github.microutils:kotlin-logging:1.7.10")
}

val publishVersion = version as String
val publicationName = "mavenSpringBootSupport"
publishing {
	publications {
		create<MavenPublication>(publicationName) {
			groupId = publishGroupId
			artifactId = SpringBootSupport.publishArtifactId
			version = publishVersion

			from(components["java"])

			pom {
				name.set(SpringBootSupport.publishName)
				description.set(SpringBootSupport.publishDescription)
				url.set(SpringBootSupport.publishProjectUrl)
				packaging = "jar"

				licenses {
					license {
						name.set(publishLicense)
					}
				}
				developers {
					developerList.forEach {
						developer {
							id.set(it["id"])
							name.set(it["name"])
							email.set(it["email"])
						}
					}
				}
				scm {
					connection.set(scmConnectionUrl)
					developerConnection.set(developerConnectionUrl)
					url.set(gitRepositoryUrl)
				}
			}

			versionMapping {
				usage("java-api") {
					fromResolutionOf("runtimeClasspath")
				}
				usage("java-runtime") {
					fromResolutionResult()
				}
			}
		}
	}

	repositories {
		maven {
			url = releaseTargetRepoUrl(version.toString())
			credentials {
				username = getProperty("MAVEN_USERNAME", project)
				password = getProperty("MAVEN_PASSWORD", project)
			}
		}
	}
}

java {
	withSourcesJar()
	withJavadocJar()
}

signing {
	val signingKey = getProperty("GPG_SIGNING_KEY", project)
	if (signingKey != null) {
		val signingPass = getProperty("GPG_SIGNING_PASSWORD", project)
		useInMemoryPgpKeys(signingKey, signingPass)
	} else {
		useGpgCmd()
	}

	sign(publishing.publications[publicationName])
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<JavaCompile> {
	sourceCompatibility = "1.8"
	targetCompatibility = "1.8"
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks {
	bootJar {
		enabled = false
	}
	jar {
		enabled = true
	}
}

