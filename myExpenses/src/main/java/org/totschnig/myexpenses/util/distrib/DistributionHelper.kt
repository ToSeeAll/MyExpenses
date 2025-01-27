package org.totschnig.myexpenses.util.distrib

import android.content.Context
import org.totschnig.myexpenses.BuildConfig
import java.time.format.DateTimeFormatter

object DistributionHelper {
    @JvmStatic
    val platform: String
        get() = distribution.platform

    @JvmStatic
    val marketPrefix: String
        get() = distribution.marketPrefix

    @JvmStatic
    val marketSelfUri: String
        get() = distribution.marketSelfUri

    @JvmStatic
    val distribution: Distribution
        get() = Distribution.valueOf(BuildConfig.DISTRIBUTION)

    @JvmStatic
    val distributionAsString: String
        get() = distribution.toString()

    @JvmStatic
    val isPlay: Boolean
        get() = distribution == Distribution.PLAY

    @JvmStatic
    val isAmazon: Boolean
        get() = distribution == Distribution.AMAZON

    @JvmStatic
    val isGithub: Boolean
        get() = distribution == Distribution.GITHUB

    /**
     * retrieve information about the current version
     *
     * @return concatenation of versionName, versionCode and buildTime
     * buildTime is automatically stored in property file during build process
     */
    @JvmStatic
    fun getVersionInfo(ctx: Context): String {
        val installer = ctx.packageManager.getInstallerPackageName(ctx.packageName)
        return "${BuildConfig.VERSION_NAME} (revision $versionNumber) $buildDateFormatted $distributionAsString $installer"
    }

    @JvmStatic
    val buildDateFormatted: String = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm").format(BuildConfig.BUILD_DATE)

    /**
     * @return version number (versionCode)
     */
    @JvmStatic
    val versionNumber: Int
        get() = BuildConfig.VERSION_CODE

    @JvmStatic
    val versionName: String
        get() = BuildConfig.VERSION_NAME

    enum class Distribution {
        PLAY {
            override val hasDynamicFeatureDelivery = true
        },
        AMAZON {
            override val marketPrefix = "amzn://apps/android?p="
        },
        GITHUB {
            override val supportsTrackingAndCrashReporting = false
        },
        HUAWEI;

        open val platform = "Android"

        open val marketPrefix= "market://details?id="

        open val marketSelfUri: String
            get() = marketPrefix + "org.totschnig.myexpenses"

        open val supportsTrackingAndCrashReporting = true

        open val hasDynamicFeatureDelivery = false
    }
}