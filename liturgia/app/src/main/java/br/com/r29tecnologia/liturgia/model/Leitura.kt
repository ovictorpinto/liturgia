package br.com.r29tecnologia.liturgia.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

/**
 * Created by victorpinto on 18/01/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Leitura(var id: Long? = null,
              var titulo: String?= null,
              var texto: String?= null,
              var data: String?= null) : Serializable {

    companion object {
        val PARAM = "leituraParam"
    }
}