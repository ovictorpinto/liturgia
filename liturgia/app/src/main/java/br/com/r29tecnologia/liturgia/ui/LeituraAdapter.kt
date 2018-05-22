package br.com.r29tecnologia.liturgia.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.r29tecnologia.liturgia.LiturgiaApplication
import br.com.r29tecnologia.liturgia.R
import br.com.r29tecnologia.liturgia.model.Leitura
import br.com.r29tecnologia.liturgia.model.Santo
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ly_convite_premium.view.*
import kotlinx.android.synthetic.main.ly_item_leitura.view.*
import kotlinx.android.synthetic.main.ly_item_santo.view.*

/**
 * Created by victorpinto on 19/01/18.
 */
class LeituraAdapter(private val list: List<Leitura>,
                     private val santo: Santo,
                     private val context: Context,
                     private val openLeitura: (Leitura) -> Unit,
                     private val openSanto: (Santo) -> Unit,
                     private val openPremium: () -> Unit) : Adapter<LeituraAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position > 0) {
            val item = list[position - 1]
            holder.bindLeitura(item)
        } else {
            if (LiturgiaApplication.PREMIUM)
                holder.bindSanto()
            else
                holder.bindConvite()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout =
                if (viewType == 0)
                    if (LiturgiaApplication.PREMIUM)
                        R.layout.ly_item_santo
                    else
                        R.layout.ly_convite_premium
                else R.layout.ly_item_leitura
        val view = LayoutInflater.from(context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindConvite() {
            itemView.button_convite.setOnClickListener {
                openPremium.invoke()
            }
        }

        fun bindSanto() {
            itemView.textview_nome_santo.text = santo.titulo
            itemView.setOnClickListener { openSanto(santo) }
            val url = santo.imagem_url
            Picasso.with(context).load(url).placeholder(R.drawable.ic_person_black_24dp).into(itemView.imageview, object : Callback {
                override fun onError() {
                }

                override fun onSuccess() {
                    itemView.progress.visibility = View.GONE
                }
            })
        }

        fun bindLeitura(item: Leitura) {

            val title = itemView.textview_titulo
            val description = itemView.textview_texto

            title.text = item.titulo
            var conteudo = ""
            item.texto?.let {
                val ini = it.indexOf("<body>")
                val fim = it.indexOf("</body>")
                val miolo = it.substring(ini, fim)
                conteudo = Html.fromHtml(miolo).toString()
            }

            description.text = conteudo
            itemView.setOnClickListener { view -> openLeitura(item) }

        }
    }

}