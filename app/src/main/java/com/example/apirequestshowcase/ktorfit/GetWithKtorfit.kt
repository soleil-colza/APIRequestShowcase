package com.example.apirequestshowcase.ktorfit

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class PokemonResponse(val sprites: Sprites)
@Serializable
data class Sprites(val frontDefault: String)

interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonResponse
}

fun main() {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    val ktorfit = Ktorfit.Builder()
        .httpClient(client)
        .baseUrl("https://pokeapi.co/api/v2/")
        .build()

    val service = ktorfit.create<PokeApiService>()

    runBlocking {
        try {
            val response = service.getPokemon(1)
            println("Pokemon Image URL: ${response.sprites.frontDefault}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        } finally {
            client.close()
        }
    }
}
