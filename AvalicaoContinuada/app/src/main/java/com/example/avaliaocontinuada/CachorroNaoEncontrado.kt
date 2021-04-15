package com.example.avaliaocontinuada

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CachorroNaoEncontrado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cachorro_nao_encontrado)

        val primeiroId = intent.getStringExtra("primeiroId")
        val segundoId = intent.getStringExtra("segundoId")

        val tvErro = findViewById<TextView>(R.id.tv_erro)
        tvErro.text = "Deu ruim... Nenhum cachorro encontrado com os ids ${primeiroId} e ${segundoId}"
    }
}
