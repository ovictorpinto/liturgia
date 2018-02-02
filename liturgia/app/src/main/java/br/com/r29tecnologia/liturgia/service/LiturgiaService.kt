package br.com.r29tecnologia.liturgia.service

import br.com.r29tecnologia.liturgia.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by victorpinto on 19/01/18.
 */
class LiturgiaService {

    private val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.LITURGIA_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

    fun getInstance() = retrofit.create<LiturgiaServiceInterface>(LiturgiaServiceInterface::class.java)

}