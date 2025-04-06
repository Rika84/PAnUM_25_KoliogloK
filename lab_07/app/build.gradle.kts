plugins {
    alias(libs.plugins.androidapplication)
    alias(libs.plugins.kotlinandroid)
    alias(libs.plugins.kotlinkapt)
}

android {
    namespace = "com.example.lab_07"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lab_07"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // AndroidX
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycleruntimektx)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    // Room
    implementation(libs.roomruntime)
    kapt(libs.roomcompiler)
    implementation(libs.roomktx)

    // Testy
    testImplementation(libs.junit)
    androidTestImplementation(libs.extjunit)
    androidTestImplementation(libs.espressocore)
}
