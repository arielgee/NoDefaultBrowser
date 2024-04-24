import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.arielg.nodefaultbrowser"
    compileSdk = 34
    val vApkBaseFileName = "NoDefaultBrowser"
    val vVersionCode = 2        // positive integer. Internal version number - determine whether one version is more recent than another
    val vVersionName = "1.1"    // string. App "regular" version
    val vBuildTimestamp = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date())

    defaultConfig {
        applicationId = "com.arielg.nodefaultbrowser"
        minSdk = 34
        targetSdk = 34
        versionCode = vVersionCode
        versionName = vVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions += "app"
    productFlavors {
        create("build-fmt-output-name") {
            dimension = "app"
            val apkName = "${vApkBaseFileName}-v${vVersionName}-${vBuildTimestamp}.apk"
            buildOutputs.all {
                val variantOutputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
                variantOutputImpl.outputFileName =  apkName
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}