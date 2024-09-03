plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "br.com.alura.leilao"
    compileSdk = 34
    defaultConfig {
        applicationId = "br.com.alura.leilao"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testBuildType = "simulado"

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
            buildConfigField("String", "URL_BASE", "\"http://192.168.0.42:8080/\"")
            buildConfigField("String", "BANCO_DADOS", "\"leilao-db\"")
        }
        create("simulado") {
            initWith(getByName("debug"))
            buildConfigField("String", "URL_BASE", "\"http://192.168.0.42:8081/\"")
            buildConfigField("String", "BANCO_DADOS", "\"leilao-teste-db\"")
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