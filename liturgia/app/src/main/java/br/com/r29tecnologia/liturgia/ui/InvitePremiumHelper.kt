package br.com.r29tecnologia.liturgia.ui

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import br.com.r29tecnologia.liturgia.R
import java.util.*

/**
 * Created by victorpinto on 22/06/17.
 */

class InvitePremiumHelper(private val context: Context) {

    companion object {
        val PREF_ULT_ABERTURA = "prefUltimaAbertura"
    }

    private val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun show() {
        val semana = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)
        val ultimoDia = sharedPref.getInt(PREF_ULT_ABERTURA, 0)

        if (semana != ultimoDia) {
            androidx.appcompat.app.AlertDialog.Builder(context).run {
                setTitle(R.string.titulo_premium)
                setView(R.layout.ly_premium_dialog)
                setPositiveButton("ok", null)
                create()
                show()
            }
            sharedPref.edit().putInt(PREF_ULT_ABERTURA, semana).apply()
        }
    }

}
