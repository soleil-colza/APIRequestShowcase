# API Request Showcase

[!Warning]

Currently, the code snippets for `KtorExample.kt` and `KtorfitExample.kt` run and return the desired image URL, but output a Kotlin compiler plugin error. I am about to investigate this on [this issue](https://github.com/soleil-colza/APIRequestShowcase/issues/1)

---

## Overview
This repository is a sample project demonstrating how to fetch Pokémon images from the PokeAPI using Ktor, Ktorfit, and Retrofit. 
It highlights the features of each library and shows how to implement API requests with them.
Any contributions are always much welcomed!!🫶🏻

## Project Structure
- ktor directory: Implementation of API requests using Ktor
- ktorfit directory: Implementation of API requests using Ktorfit
- retrofit directory: Implementation of API requests using Retrofit

### Ktor
Ktor is an asynchronous and lightweight web framework for Kotlin. 
ktor/KtorExample.kt showcases how to make a request to the PokeAPI using Ktor.

```
fun main() {
    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    runBlocking {
        val response: HttpResponse = client.get("https://pokeapi.co/api/v2/pokemon/1")
        println(response.readText())
    }
}
```

### Ktorfit
Ktorfit is a library that enables Retrofit-like API requests using Ktor. 
ktorfit/KtorfitExample.kt showcases how to make a request to the PokeAPI using Ktorfit.

```
interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): String
}

fun main() {
    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    val ktorfit = Ktorfit.Builder()
        .httpClient(client)
        .baseUrl("https://pokeapi.co/api/v2/")
        .build()

    val service = ktorfit.create<PokeApiService>()

    runBlocking {
        val response = service.getPokemon(1)
        println(response)
    }
}
```

### Retrofit
Retrofit is a type-safe HTTP client for Android and Java. 
retrofit/RetrofitExample.kt showcases how to make a request to the PokeAPI using Retrofit.

```
interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): String
}

fun main() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(PokeApiService::class.java)

    runBlocking {
        val response = service.getPokemon(1)
        println(response)
    }
}
```
