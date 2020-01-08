package br.com.r29tecnologia.liturgia

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics


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
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)
        }
    }
}