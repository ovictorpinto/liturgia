package br.com.r29tecnologia.liturgia.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.r29tecnologia.liturgia.R
import kotlinx.android.synthetic.main.ly_main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ly_main_activity)

        setSupportActionBar(toolbar)

        val mSectionsPagerAdapter = DiaAdapter(supportFragmentManager)

        viewpager.adapter = mSectionsPagerAdapter
        viewpager.currentItem = DiaAdapter.INITIAL_POSITION
        tablayout.setupWithViewPager(viewpager)
    }
}
