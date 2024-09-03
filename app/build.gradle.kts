import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
}

val properties = Properties()
properties.load(FileInputStream(file("project.properties")))

android {
    namespace = properties.getProperty("namespace")
    compileSdk = properties.getProperty("compileSdk").toInt()

    defaultConfig {
        applicationId = properties.getProperty("namespace")
        minSdk = properties.getProperty("minSdk").toInt()
        targetSdk = properties.getProperty("targetSdk").toInt()
        versionCode = properties.getProperty("versionCode").toInt()
        versionName = properties.getProperty("versionName")
        testInstrumentationRunner = properties.getProperty("testInstrumentationRunner")
    }

    testBuildType = properties.getProperty("testBuildType")

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField(
                "String",
                "URL_BASE",
                "\"${properties.getProperty("debug.URL_BASE")}\""
            )
            buildConfigField(
                "String",
                "BANCO_DADOS",
                "\"${properties.getProperty("debug.BANCO_DADOS")}\""
            )
        }
        create("simulado") {
            initWith(getByName("debug"))
            buildConfigField(
                "String",
                "URL_BASE",
                "\"${properties.getProperty("simulado.URL_BASE")}\""
            )
            buildConfigField(
                "String",
                "BANCO_DADOS",
                "\"${properties.getProperty("simulado.BANCO_DADOS")}\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.android)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.espresso.contrib)

    testImplementation(libs.junit)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.hamcrest.all)
    testImplementation(libs.mockito.core)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.retrofit)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.gson.converter)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)

    ksp(libs.room.compiler)
}