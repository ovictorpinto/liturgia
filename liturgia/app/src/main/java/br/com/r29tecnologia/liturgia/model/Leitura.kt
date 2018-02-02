package br.com.r29tecnologia.liturgia.model

import java.io.Serializable

/**
 * Created by victorpinto on 18/01/18.
 */
class Leitura(var id: Long? = null,
              var titulo: String?= null,
              var texto: String?= null,
              var data: String?= null) : Serializable {

    companion object {
        val PARAM = "leituraParam"
    }
}