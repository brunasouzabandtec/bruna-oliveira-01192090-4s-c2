package com.example.avaliaocontinuada

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CachorroEncontrado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cachorro_encontrado)

        val raca1 = intent.getStringExtra("raca1")
        val raca2 = intent.getStringExtra("raca2")
        val precoTotal = intent.getDoubleExtra("precoTotal", 0.0)

        val tvPrimeiroDog = findViewById<TextView>(R.id.tv_primeiro_dog)
        val tvSegundoDog = findViewById<TextView>(R.id.tv_segundo_dog)
        val tvPrecoTotal = findViewById<TextView>(R.id.tv_preco_total)

        tvPrimeiroDog.text = "Cachorro 1: ${raca1}"
        tvSegundoDog.text = "Cachorro 2: ${raca2}"
        tvPrecoTotal.text = "Preço total de compra: R$${precoTotal}"
    }
}
