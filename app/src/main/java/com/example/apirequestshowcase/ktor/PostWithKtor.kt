package com.example.apirequestshowcase.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class PokemonResponse(val sprites: Sprites)
@Serializable
data class Sprites(val front_default: String)

fun main() {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    runBlocking {
        try {
            val response: PokemonResponse = client.get("https://pokeapi.co/api/v2/pokemon/1").body()
            println("Pokemon Image URL: ${response.sprites.front_default}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        } finally {
            client.close()
        }
    }
}
