package br.com.r29tecnologia.liturgia

import android.app.Application
import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics
import io.fabric.sdk.android.Fabric;


/**
 * Created by victorpinto on 18/01/18.
 */
class LiturgiaApplication : Application() {
    companion object {
        var PREMIUM = false
        var PURCHASE_TOKEN: String? = null
        val ID_PREMIUM = "premium"
        val ACTION_PURCHASE = "comprouAction"
    }

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)
        }
    }
}