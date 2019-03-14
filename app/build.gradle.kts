import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import org.gradle.internal.impldep.com.amazonaws.PredefinedClientConfigurations.defaultConfig
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import com.biblialibras.android.buildsrc.Libs
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt
import java.io.FileInputStream
import java.util.*

apply {
    from("../dependencies.gradle")
}

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("com.github.ben-manes.versions")
}

androidExtensions {
    isExperimental = true
}

android {
    signingConfigs {
        register("release") {
            val keystorePropertiesFile = file("../upload-keystore.properties")

            if (!keystorePropertiesFile.exists()) {
                logger.warn("Release builds may not work: signing config not found.")
                return@register
            }

            val keystoreProperties = Properties()
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))

            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.biblialibras.android"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 44
        versionName = "3.0.8"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        named("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            extra.apply {
                set("alwaysUpdateBuildId", false)
                set("enableCrashlytics", false)
            }
        }
        named("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
                )
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    lintOptions {
        isAbortOnError = false
//        check "Interoperability"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    dataBinding.isEnabled = true
    kapt.correctErrorTypes = true

}

dependencies {

    val firebase_core = "16.0.6"
    val firebase_firestore = "17.1.5"
    val gms = "15.0.1"
    val glide = "4.8.0"

    implementation(project(":repo"))
    implementation(project(":sharedmainscreen"))
    implementation(project(":common"))
    implementation(project(":bible"))

    // Dagger
    implementation(Libs.Dagger.androidSupport)
    kapt(Libs.Dagger.compiler)
    kapt(Libs.Dagger.androidProcessor)

    compileOnly(Libs.AssistedInject.annotationDagger2)
    kapt(Libs.AssistedInject.processorDagger2)

    // RX
    implementation("com.github.frangsierra:rx2firebase:1.5.7")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.7")
    implementation("com.jakewharton.rxrelay2:rxrelay:2.1.0")
    implementation("io.reactivex.rxjava2:rxkotlin:2.3.0")

    // RecyclerView
    implementation("com.xwray:groupie:2.3.0")
    implementation("com.xwray:groupie-kotlin-android-extensions:2.3.0")
    implementation("com.yarolegovich:discrete-scrollview:1.4.9")

    // Epoxy
    val epoxy = "3.3.0"
    implementation("com.airbnb.android:epoxy:$epoxy")
    implementation("com.airbnb.android:epoxy-paging:$epoxy")
    implementation("com.airbnb.android:epoxy-databinding:$epoxy")
    kapt("com.airbnb.android:epoxy-processor:$epoxy")
    implementation("com.airbnb.android:mvrx:0.7.2")


    // Glide
    implementation("com.github.bumptech.glide:glide:$glide")
    implementation("com.github.bumptech.glide:recyclerview-integration:$glide")
    kapt("com.github.bumptech.glide:compiler:$glide")

    // Arch
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation("androidx.room:room-runtime:2.0.0")
    implementation("androidx.room:room-rxjava2:2.0.0")
    kapt("androidx.room:room-compiler:2.0.0")

    implementation("com.squareup.sqldelight:android-driver:1.0.3")


    // KTX
    implementation("androidx.core:core-ktx:1.0.1")

    // Support
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.dynamicanimation:dynamicanimation:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.browser:browser:1.0.0")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.media:media:1.0.1")

    val navVersion = "1.0.0-rc01"
    implementation("android.arch.navigation:navigation-fragment-ktx:$navVersion")
    implementation("android.arch.navigation:navigation-ui-ktx:$navVersion")


    val paging_version = "2.1.0"
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
    implementation("androidx.paging:paging-rxjava2-ktx:$paging_version")

    // Coroutines
    val coroutines_version = "1.1.1"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    // Iconics
    implementation("com.mikepenz:iconics-core:3.1.0@aar")
    implementation("com.mikepenz:community-material-typeface:2.0.46.1@aar")
    implementation("com.mikepenz:google-material-typeface:3.0.1.2.original@aar")

    // VideoItem Player
    implementation("com.github.evgenyneu:js-evaluator-for-android:5.0.0")
    implementation("com.devbrackets.android:exomedia:4.3.0")

    val exoVersion = "2.9.1"

    implementation("com.google.android.exoplayer:extension-mediasession:$exoVersion")
    implementation("com.google.android.exoplayer:extension-okhttp:$exoVersion")

    // Logging
    implementation("com.orhanobut:logger:2.2.0")

    implementation("com.crashlytics.sdk.android:crashlytics:2.9.9")

    // Dialogs
    implementation("com.afollestad.material-dialogs:core:2.0.3")

    // About
    implementation("com.github.daniel-stoneuk:material-about-library:2.4.2")
    implementation("me.zhanghai.android.materialprogressbar:library:1.6.1")

    // Internal
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.squareup.okhttp3:okhttp:3.13.1")
    implementation("com.jonathanfinerty.once:once:1.2.2")
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))

    implementation("com.squareup.sqldelight:android-driver:1.0.3")
    implementation("com.squareup.sqldelight:android-paging-extensions:1.0.3")

    // Debugging
    implementation("com.facebook.stetho:stetho:1.5.0")

    // testCompile "com.squareup.leakcanary:leakcanary-android-no-op:1.5.1"
    // debugCompile "com.squareup.leakcanary:leakcanary-android:1.5.1"
    // releaseCompile "com.squareup.leakcanary:leakcanary-android-no-op:1.5.1"

    implementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.1.1")
}
