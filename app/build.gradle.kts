plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.mio.plugin.renderer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mio.plugin.renderer"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
signingConfig = signingConfigs.getByName("debug")
        }
        configureEach {
            // 应用名 (可保持为 "LTW Renderer")
            resValue("string", "app_name", "LTW Renderer")
            applicationIdSuffix = ".ltw"

            // 【必改】渲染器在启动器内显示的名称
            manifestPlaceholders["des"] = "LTW"

            // 【必改】渲染器定义，格式为 名称:渲染器库名:EGL库名
            // LTW 的核心动态库就是 libltw.so
            manifestPlaceholders["renderer"] = "LTW:libltw.so:libltw.so"

            // 特殊 Env 留空即可，LTW 不需要额外的 DLOPEN
            manifestPlaceholders["boatEnv"] = mutableMapOf<String, String>().apply {}.run {
                var env = ""
                forEach { (key, value) -> env += "$key=$value:" }
                env.dropLast(1)
            }

            manifestPlaceholders["pojavEnv"] = mutableMapOf<String, String>().apply {}.run {
                var env = ""
                forEach { (key, value) -> env += "$key=$value:" }
                env.dropLast(1)
            }

            // 【必改】支持的最低与最高 MC 版本
            // LTW 主要面向 1.17 及以上，上限可设为当前快照版本
            manifestPlaceholders["minMCVer"] = "1.17"
            manifestPlaceholders["maxMCVer"] = "1.21.5"
        }
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
    implementation(files("libs/ltw-release.aar"))
}
