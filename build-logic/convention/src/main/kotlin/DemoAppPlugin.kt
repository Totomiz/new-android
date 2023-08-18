import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.newandroid.kscript.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class DemoAppPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            pluginManager.applyPlugin()
            val extension = extensions.getByType<ApplicationExtension>()
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = ConfigValue.TARGET_SDK_VERSION
                configureGradleManagedDevices(this)
            }

            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(this)
            }
            configureAndroidCompose(extension)
            configAndroidCommonLibs()
            configMyComposeLibs()
            configAsAppLibs()
        }
    }

    private fun PluginManager.applyPlugin() {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")
    }
}