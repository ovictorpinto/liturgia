package br.com.r29tecnologia.liturgia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import br.com.r29tecnologia.liturgia.R
import br.com.r29tecnologia.liturgia.model.Santo
import kotlinx.android.synthetic.main.ly_leitura_detalhe.*

/**
 * Created by victorpinto on 19/01/18.
 */
class DetalheSantoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ly_leitura_detalhe)
        //mantém a tela ativa
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        var santo = intent.getSerializableExtra(Santo.PARAM) as Santo
        webview.loadDataWithBaseURL(null, santo.texto, "text/html", "utf-8", null)
        this.setTitle(santo.titulo)
    }
}