package br.com.r29tecnologia.liturgia.service

import br.com.r29tecnologia.liturgia.model.Leitura
import br.com.r29tecnologia.liturgia.model.RetornoPadrao
import br.com.r29tecnologia.liturgia.model.Santo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by victorpinto on 18/01/18.
 */
interface LiturgiaServiceInterface {

    @GET("api/v3/liturgia/leituras/{data}")
    fun leiturasEm(@Path("data") data: String): Call<RetornoPadrao<Leitura>>

    @GET("api/v1/santo/{data}")
    fun santoEm(@Path("data") data: String): Call<RetornoPadrao<Santo>>
}