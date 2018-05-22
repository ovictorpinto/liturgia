package br.com.r29tecnologia.liturgia

import android.app.Application
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


/**
 * Created by victorpinto on 18/01/18.
 */
class LiturgiaApplication : Application() {
    companion object {
        var PREMIUM = false
        val ID_PREMIUM = "premium"
    }

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
    }
}