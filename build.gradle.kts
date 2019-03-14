buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.0-alpha07")
        classpath(kotlin("gradle-plugin", version = "1.3.21"))
        classpath("com.github.ben-manes:gradle-versions-plugin:0.21.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
