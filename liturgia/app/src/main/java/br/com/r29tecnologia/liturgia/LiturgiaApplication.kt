package br.com.r29tecnologia.liturgia

import android.app.Application
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


/**
 * Created by victorpinto on 18/01/18.
 */
class LiturgiaApplication : Application() {
    companion object {
        val PREMIUM = false
    }
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }
}