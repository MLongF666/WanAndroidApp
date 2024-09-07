plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}
kapt{
    generateStubs = true
}

android {
    namespace = "com.mlf.wanandroid"
    compileSdk = 34

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    kapt{
        generateStubs = true
    }
    defaultConfig {
        applicationId = "com.mlf.wanandroid"
        minSdk = 25
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
//    implementation  (libs.refresh.layout.kernel)   //核心必须依赖
//    implementation  (libs.refresh.header.classics)   //经典刷新头
//    implementation  (libs.refresh.header.radar)   //雷达刷新头
//    implementation  (libs.refresh.header.falsify)   //虚拟刷新头
//    implementation  (libs.refresh.header.material)   //谷歌刷新头
//    implementation  (libs.refresh.header.two.level)   //二级刷新头
//    implementation  (libs.refresh.footer.ball)   //球脉冲加载
//    implementation  (libs.refresh.footer.classics)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.room.ktx)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    kapt(libs.androidx.databinding)
    kapt(libs.androidx.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}