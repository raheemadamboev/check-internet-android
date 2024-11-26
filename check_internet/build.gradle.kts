plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin)
    `maven-publish`
}

android {
    namespace = "xyz.teamgravity.checkinternet"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
    }

    lint {
        targetSdk = libs.versions.sdk.target.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = libs.versions.java.target.get()
    }

    packaging {
        resources {
            pickFirsts.add("META-INF/atomicfu.kotlin_module")
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {

    // annotation
    implementation(libs.annotation)

    // coroutines
    implementation(libs.coroutines)
    implementation(libs.coroutines.android)
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.raheemadamboev"
                artifactId = "check-internet-android"
                version = "1.1.2"
            }
        }
    }
}