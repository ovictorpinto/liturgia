package br.com.r29tecnologia.liturgia.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import br.com.r29tecnologia.liturgia.LiturgiaApplication
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by victorpinto on 22/01/18.
 */
class DiaAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val TOTAL_FREE = 3
        val TOTAL_PREMIUM = 61
    }

    private val today = Date()
    private val diaMesFormatter = SimpleDateFormat("dd/MM")

    override fun getItem(position: Int): Fragment {
        val frag: Fragment = DiaFragment.newInstance(getDateAt(position))
        return frag
    }

    override fun getCount(): Int {
        return if (LiturgiaApplication.PREMIUM) TOTAL_PREMIUM else TOTAL_FREE
    }

    override fun getPageTitle(position: Int): CharSequence? {

        val dtTrainning = getDateAt(position)

        val initialPosition = getInitialPosition()
        when (position) {
            initialPosition -> return "Hoje " + diaMesFormatter.format(dtTrainning)
            initialPosition + 1 -> return "Amanhã " + diaMesFormatter.format(dtTrainning)
            initialPosition - 1 -> return "Ontem " + diaMesFormatter.format(dtTrainning)
        }

        return SimpleDateFormat("EEE dd/MM").format(dtTrainning)
    }

    fun getInitialPosition(): Int {
        return count / 2
    }

    private fun getDateAt(position: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = today
        calendar.add(Calendar.DATE, position - getInitialPosition())
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

}