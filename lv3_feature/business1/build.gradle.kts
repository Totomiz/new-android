
val business1AsApp: Boolean = project.property("business1_as_app")?.toString()?.toBoolean() ?: false

//println("business1_as_app: $business1AsApp  ")
//if(business1AsApp){
//    apply(from = "./buildAsApp.gradle.kts")
//}else{
//
//}
plugins {
    id("newandroid.android.library")
}

android {
    namespace = "com.tdk.lv1.ui"
}

dependencies {


}
