import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.isIncludeCompileClasspath

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("jacoco")

}

jacoco {
    toolVersion = "0.8.7"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

android {
    namespace = "com.merttoptas.devnottestingdemo"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.merttoptas.devnottestingdemo"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    //Hilt
    implementation(libs.hilt.android)
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android.testing)

    testImplementation(libs.turbine)
    testImplementation(libs.truth)

    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    implementation(libs.androidx.navigation.testing)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

tasks.register("jacocoTestReport", JacocoReport::class) {
    group = "Reporting"
    description = "Generate Jacoco coverage reports after running tests."

    dependsOn("testBetaUnitTest", "createBetaCoverageReport")

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/*Dagger*.*",
        "**/*Hilt*.*",
        "**/*_MembersInjector.class",
        "**/Dagger*Component.class",
        "**/Dagger*Component\$Builder.class",
        "**/*Module_*Factory.class",
        "**/*Module.kt",
        "**/*_Factory*.*",
        "**/*_Provide*Factory*.*",
        "**/di/**",
        "dagger.hilt.internal/*",
        "hilt_aggregated_deps/*",
        )


    val mainSrc = "${project.projectDir}/src/main/java"

    val classDirectories: ConfigurableFileCollection = project.objects.fileCollection()
    val javaTree = fileTree(mapOf("dir" to mainSrc, "exclude" to fileFilter))
    val kotlinTree = fileTree(
        mapOf(
            "dir" to "$buildDir/tmp/kotlin-classes/${project.name}",
            "exclude" to fileFilter
        )
    )


    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(javaTree, kotlinTree))

    executionData.setFrom(
        fileTree(project.buildDir).include(
            "jacoco/testBetaUnitTest.exec",
            "outputs/code-coverage/connected/*coverage.ec"
        )
    )
}

tasks.register("getCoverage", Exec::class) {
    group = "Reporting"
    dependsOn("jacocoTestReport")
    commandLine("open", "${buildDir}/reports/jacoco/jacocoTestReport/html/index.html")
}
