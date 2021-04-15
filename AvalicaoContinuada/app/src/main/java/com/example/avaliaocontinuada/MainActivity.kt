package com.example.avaliaocontinuada

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun irParaTelaDeNaoEncontrado(primeiroId: String, segundoId: String) {
        val cachorroNaoEncontrado =
            Intent(this@MainActivity, CachorroNaoEncontrado::class.java)

        cachorroNaoEncontrado.putExtra("primeiroId", primeiroId)
        cachorroNaoEncontrado.putExtra("segundoId", segundoId)

        startActivity(cachorroNaoEncontrado)
    }

    fun comprar(view: View) {
        val apiCachorros = ConexaoApiCachorros.criar()

        val etPrimeiroId: EditText = findViewById(R.id.et_primeiro_id)
        val etSegundoId: EditText = findViewById(R.id.et_segundo_id)
        val swAmigavel: Switch = findViewById(R.id.sw_amigavel)

        val amigavel = swAmigavel.isChecked
        val primeiroId = etPrimeiroId.text.toString()
        val segundoId = etSegundoId.text.toString()

        apiCachorros.get(primeiroId).enqueue(object : Callback<Cachorro> {
            override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {
                val cachorro1 = response.body()
                apiCachorros.get(segundoId).enqueue(object : Callback<Cachorro> {
                    override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {
                        val cachorro2 = response.body()

                        if (cachorro1 == null && cachorro2 == null) {
                            irParaTelaDeNaoEncontrado(primeiroId, segundoId)

                            return
                        }

                        if (amigavel) {
                            val cachorro1Amigavel = cachorro1?.indicadoCriancas
                            val cachorro2Amigavel = cachorro2?.indicadoCriancas
                            val nenhumAmigavel = cachorro1Amigavel == false && cachorro2Amigavel == false

                            if ((cachorro1Amigavel == false && cachorro2 == null) || (cachorro2Amigavel == false && cachorro1 == null) || nenhumAmigavel) {
                                irParaTelaDeNaoEncontrado(primeiroId, segundoId)

                                return
                            }
                        }

                        val cachorroEncontrado =
                            Intent(this@MainActivity, CachorroEncontrado::class.java)

                        var raca1 = "(não encontrado)"
                        var raca2 = "(não encontrado)"
                        var preco1 = 0.0
                        var preco2 = 0.0

                        if (cachorro1 != null) {
                            if (amigavel && !cachorro1.indicadoCriancas) {
                                // Não fazer nada. Não é um cachorro amigável.
                            } else {
                                raca1 = cachorro1.raca
                                preco1 = cachorro1.precoMedio
                            }
                        }

                        if (cachorro2 != null) {
                            if (amigavel && !cachorro2.indicadoCriancas) {
                                // Não fazer nada. Não é um cachorro amigável.
                            } else {
                                raca2 = cachorro2.raca
                                preco2 = cachorro2.precoMedio
                            }
                        }

                        cachorroEncontrado.putExtra("raca1", raca1)
                        cachorroEncontrado.putExtra("raca2", raca2)
                        cachorroEncontrado.putExtra("precoTotal", preco1 + preco2)

                        startActivity(cachorroEncontrado)
                    }

                    override fun onFailure(call: Call<Cachorro>, t: Throwable) {
                        Toast.makeText(baseContext, "Erro: ${t.message!!}", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }

            override fun onFailure(call: Call<Cachorro>, t: Throwable) {
                Toast.makeText(baseContext, "Erro: ${t.message!!}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
