package com.example.apirequestshowcase.retrofit

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class PokemonResponse(@SerializedName("sprites") val sprites: Sprites)
data class Sprites(@SerializedName("front_default") val frontDefault: String)

interface PokeApiService {
    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<PokemonResponse>
}

fun main() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(PokeApiService::class.java)

    val call = service.getPokemon(1)
    call.enqueue(object : Callback<PokemonResponse> {
        override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
            if (response.isSuccessful) {
                val pokemon = response.body()
                println("Pokemon Image URL: ${pokemon?.sprites?.frontDefault}")
            } else {
                println("Response Code: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
            println("Error: ${t.message}")
        }
    })
}
