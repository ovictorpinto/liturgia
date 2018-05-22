package br.com.r29tecnologia.liturgia.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.r29tecnologia.liturgia.LiturgiaApplication
import br.com.r29tecnologia.liturgia.R
import com.android.billingclient.api.*
import kotlinx.android.synthetic.main.ly_main_activity.*

class MainActivity : AppCompatActivity(), PurchasesUpdatedListener {

    lateinit private var billingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ly_main_activity)

        setSupportActionBar(toolbar)

        billingClient = BillingClient.newBuilder(this).setListener(this).build()
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
        viewpager.currentItem = DiaAdapter.INITIAL_POSITION
        tablayout.setupWithViewPager(viewpager)
    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
            if (purchases.filter { LiturgiaApplication.ID_PREMIUM.equals(it.sku, true) }.isNotEmpty()) {
                LiturgiaApplication.PREMIUM = true
                viewpager.adapter?.notifyDataSetChanged()
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
        if (queryPurchases.purchasesList.filter { LiturgiaApplication.ID_PREMIUM.equals(it.sku, true) }.isNotEmpty()) {
            LiturgiaApplication.PREMIUM = true
        }
        resume()
    }
}
