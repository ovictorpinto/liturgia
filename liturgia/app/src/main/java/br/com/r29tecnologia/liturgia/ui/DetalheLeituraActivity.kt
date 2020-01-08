package br.com.r29tecnologia.liturgia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import br.com.r29tecnologia.liturgia.model.Leitura
import kotlinx.android.synthetic.main.ly_leitura_detalhe.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebSettings

/**
 * Created by victorpinto on 19/01/18.
 */
class DetalheLeituraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(br.com.r29tecnologia.liturgia.R.layout.ly_leitura_detalhe)
        //mantém a tela ativa
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        var leitura = intent.getSerializableExtra(Leitura.PARAM) as Leitura
        webview.loadDataWithBaseURL(null, leitura.texto, "text/html", "utf-8", null)
        this.setTitle(leitura.titulo)
    }
}