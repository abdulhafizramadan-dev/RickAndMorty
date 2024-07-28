plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.gojek.rickandmorty"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gojek.rickandmorty"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(project(":base"))
    implementation(project(":features:characters:characters"))
    implementation(project(":features:characters:characters-shared-domain"))

    implementation(libs.shimmer)
    implementation(libs.multistateview)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.retrofit)
    implementation(libs.adapter.rxjava2)
    implementation(libs.converter.gson)

    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.rxbinding.kotlin)

    implementation(libs.glide)

    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}