package br.com.r29tecnologia.liturgia.ui

import br.com.r29tecnologia.liturgia.model.Leitura
import br.com.r29tecnologia.liturgia.model.Santo

/**
 * Created by victorpinto on 22/01/18.
 */
interface OnItemLeituraClickListener {
    fun onClick(leitura: Leitura)

    fun onClick(santo: Santo)
}