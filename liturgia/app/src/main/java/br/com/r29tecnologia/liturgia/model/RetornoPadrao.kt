package br.com.r29tecnologia.liturgia.model

/**
 * Created by victorpinto on 18/01/18.
 */
class RetornoPadrao<T>(var  status: Boolean? = null, var data : List<T>? = null, var erro :String? = null) {
}