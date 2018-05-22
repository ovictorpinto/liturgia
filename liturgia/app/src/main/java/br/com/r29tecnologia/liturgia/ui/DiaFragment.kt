package br.com.r29tecnologia.liturgia.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.r29tecnologia.liturgia.R
import br.com.r29tecnologia.liturgia.model.Leitura
import br.com.r29tecnologia.liturgia.model.RetornoPadrao
import br.com.r29tecnologia.liturgia.model.Santo
import br.com.r29tecnologia.liturgia.service.LiturgiaService
import kotlinx.android.synthetic.main.ly_dia.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by victorpinto on 22/01/18.
 */
class DiaFragment : Fragment() {


    lateinit var leiturasEm: Call<RetornoPadrao<Leitura>>
    lateinit var santoEm: Call<RetornoPadrao<Santo>>
    private var leituras: List<Leitura>? = null
    private var santo: Santo? = null
    private var purchiseListener: MainActivity? = null

    companion object {

        val PARAM_DIA = "paramDia"

        fun newInstance(dia: Date): Fragment {
            val frag = DiaFragment()
            val arguments = Bundle()
            arguments.putSerializable(PARAM_DIA, dia)
            frag.arguments = arguments
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.ly_dia, container, false)

        var dia = arguments!![PARAM_DIA]
        var diaFormatado = SimpleDateFormat("yyyy-MM-dd").format(dia)
        leiturasEm = LiturgiaService().getInstance().leiturasEm(diaFormatado)
        leiturasEm.enqueue(object : Callback<RetornoPadrao<Leitura>> {
            override fun onFailure(call: Call<RetornoPadrao<Leitura>>?, t: Throwable?) {
                t?.printStackTrace()
                if (!leiturasEm.isCanceled) {
                    progress.visibility = View.GONE
                    layout_failure.visibility = View.VISIBLE
                }
            }

            override fun onResponse(call: Call<RetornoPadrao<Leitura>>?, response: Response<RetornoPadrao<Leitura>>?) {
                response?.body()?.let {
                    if (it.status!!) {
                        leituras = it.data!!
                        fillFields()
                    } else {
                        //mostra o erro
                    }
                }
            }
        })

        santoEm = LiturgiaService().getInstance().santoEm(diaFormatado)
        santoEm.enqueue(object : Callback<RetornoPadrao<Santo>> {
            override fun onFailure(call: Call<RetornoPadrao<Santo>>?, t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onResponse(call: Call<RetornoPadrao<Santo>>?, response: Response<RetornoPadrao<Santo>>?) {
                response?.body()?.let {
                    if (it.status!!) {
                        santo = it.data!![0]
                        fillFields()
                    }
                }
            }

        })

        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        leiturasEm.cancel()
        santoEm.cancel()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            purchiseListener = context
        }
    }

    private fun fillFields() {
        if (leituras != null && santo != null) {
            progress.visibility = View.GONE
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.adapter = LeituraAdapter(leituras!!, santo!!, context!!,
                    {
                        var intent = Intent(context, DetalheLeituraActivity::class.java)
                        intent.putExtra(Leitura.PARAM, it)
                        startActivity(intent)
                    },
                    {
                        var intent = Intent(context, DetalheSantoActivity::class.java)
                        intent.putExtra(Santo.PARAM, it)
                        startActivity(intent)
                    },
                    {
                        purchiseListener?.onSelectPremiumPurchase()
                    })
        }
    }
}