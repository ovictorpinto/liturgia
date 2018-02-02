package br.com.r29tecnologia.liturgia.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by victorpinto on 22/01/18.
 */
class DiaAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        val INITIAL_POSITION = 1
        val TOTAL = 3
    }

    private val today = Date()
    private val diaMesFormatter = SimpleDateFormat("dd/MM")

    override fun getItem(position: Int): Fragment {
        val frag: Fragment = DiaFragment.newInstance(getDateAt(position))
        return frag
    }

    override fun getCount(): Int {
        return TOTAL
    }

    override fun getPageTitle(position: Int): CharSequence? {

        val dtTrainning = getDateAt(position)
        when (position) {
            INITIAL_POSITION -> return "Hoje " + diaMesFormatter.format(dtTrainning)
            INITIAL_POSITION + 1 -> return "Amanhã " + diaMesFormatter.format(dtTrainning)
            INITIAL_POSITION - 1 -> return "Ontem " + diaMesFormatter.format(dtTrainning)
        }

        return SimpleDateFormat("EEE dd/MM").format(dtTrainning)
    }

    private fun getDateAt(position: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = today
        calendar.add(Calendar.DATE, position - INITIAL_POSITION)
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

}