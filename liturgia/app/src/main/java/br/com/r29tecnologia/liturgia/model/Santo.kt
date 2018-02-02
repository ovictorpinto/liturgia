package br.com.r29tecnologia.liturgia.model

import java.io.Serializable

/**
 * Created by victorpinto on 18/01/18.
 */
class Santo(var id: Long? = null,
            var titulo: String?= null,
            var texto: String?= null,
            var url: String?= null,
            var imagem_url: String?= null,
            var dia: String?= null) : Serializable {

    companion object {
        val PARAM = "paramSanto"
    }
}