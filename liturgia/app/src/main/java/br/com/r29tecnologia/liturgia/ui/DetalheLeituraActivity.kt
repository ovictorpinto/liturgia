package br.com.r29tecnologia.liturgia.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import br.com.r29tecnologia.liturgia.R
import br.com.r29tecnologia.liturgia.model.Leitura
import kotlinx.android.synthetic.main.ly_leitura_detalhe.*

/**
 * Created by victorpinto on 19/01/18.
 */
class DetalheLeituraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ly_leitura_detalhe)
        //mantém a tela ativa
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        var leitura = intent.getSerializableExtra(Leitura.PARAM) as Leitura
        webview.loadData(leitura.texto, "text/html; charset=UTF-8", null)
        this.setTitle(leitura.titulo)
    }
}