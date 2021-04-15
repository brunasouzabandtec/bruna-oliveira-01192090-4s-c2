package com.example.avaliaocontinuada

import retrofit2.Call
import retrofit2.http.*

interface ApiCachorros {
    @GET("{id}")
    fun get(@Path("id") id:String): Call<Cachorro>
}
