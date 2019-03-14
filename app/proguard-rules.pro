# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/apple/Softwares/adt-bundle-mac-x86_64-20140702/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#-keep class com.mikepenz.iconics.** { *; }
#-keep class com.mikepenz.community_material_typeface_library.CommunityMaterial
#-keep class com.mikepenz.google_material_typeface_library.GoogleMaterial

# Rules for OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**

# Rules for Junit
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Rules for ExoMedia
-dontwarn com.devbrackets.android.exomedia.ui.widget.VideoControlsMobile

# Rules for RxFirebase
-dontwarn durdinapps.rxfirebase2.**

# Need to check later
-dontwarn com.biblialibras.android.groupie.**