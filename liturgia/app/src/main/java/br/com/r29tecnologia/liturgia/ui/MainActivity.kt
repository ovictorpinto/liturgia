package br.com.r29tecnologia.liturgia.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.r29tecnologia.liturgia.BuildConfig
import br.com.r29tecnologia.liturgia.LiturgiaApplication
import br.com.r29tecnologia.liturgia.R
import br.com.r29tecnologia.liturgia.R.id.*
import com.android.billingclient.api.*
import kotlinx.android.synthetic.main.ly_main_activity.*

class MainActivity : AppCompatActivity(), PurchasesUpdatedListener {

    lateinit private var billingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ly_main_activity)

        setSupportActionBar(toolbar)

        billingClient = BillingClient.newBuilder(this).setListener(this).build()
        if (billingClient.isFeatureSupported(BillingClient.FeatureType.IN_APP_ITEMS_ON_VR) != BillingClient.BillingResponse.OK) {
            resume()
        }
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    // The billing client is ready. You can query purchases here.
                    verifyPremium()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                resume()
            }
        })
    }

    private fun resume() {

        val mSectionsPagerAdapter = DiaAdapter(supportFragmentManager)

        viewpager.adapter = mSectionsPagerAdapter
        viewpager.currentItem = mSectionsPagerAdapter.getInitialPosition()
        tablayout.setupWithViewPager(viewpager)
    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
            if (purchases.filter { LiturgiaApplication.ID_PREMIUM.equals(it.sku, true) }.isNotEmpty()) {
                LiturgiaApplication.PREMIUM = true
                viewpager.adapter?.notifyDataSetChanged()
                invalidateOptionsMenu()
                LocalBroadcastManager.getInstance(MainActivity@ this).sendBroadcast(Intent(LiturgiaApplication.ACTION_PURCHASE))
            }
        }
    }

    fun onSelectPremiumPurchase() {
        val flowParams = BillingFlowParams.newBuilder()
                .setSku(LiturgiaApplication.ID_PREMIUM)
                .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription
                .build()
        billingClient.launchBillingFlow(this, flowParams)
    }

    private fun verifyPremium() {

        var queryPurchases = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
        val filter = queryPurchases.purchasesList.filter { LiturgiaApplication.ID_PREMIUM.equals(it.sku, true) }
        if (filter.isNotEmpty()) {
            LiturgiaApplication.PREMIUM = true
            LiturgiaApplication.PURCHASE_TOKEN = filter[0].purchaseToken
        } else {
            InvitePremiumHelper(this).show()
        }
        resume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        if (BuildConfig.DEBUG && LiturgiaApplication.PREMIUM)
            inflater.inflate(R.menu.menu_remove_premium, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_remove_premium) {
            billingClient.consumeAsync(LiturgiaApplication.PURCHASE_TOKEN, { responseCode, purchaseToken ->
                if (responseCode == BillingClient.BillingResponse.OK) {
                    Toast.makeText(this, "Removeu", Toast.LENGTH_SHORT).show()
                    LiturgiaApplication.PREMIUM = false
                    LiturgiaApplication.PURCHASE_TOKEN = null
                    viewpager.adapter?.notifyDataSetChanged()
                    invalidateOptionsMenu()
                    LocalBroadcastManager.getInstance(MainActivity@ this).sendBroadcast(Intent(LiturgiaApplication.ACTION_PURCHASE))
                } else {
                    Toast.makeText(this, "Não removeu ${responseCode}", Toast.LENGTH_SHORT).show()
                }
            })
            return true
        } else
            return super.onOptionsItemSelected(item)
    }
}
