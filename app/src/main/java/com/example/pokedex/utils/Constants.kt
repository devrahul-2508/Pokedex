package com.example.pokedex.utils

object Constants {

    const val BASE_URL = "https://pokeapi.co/api/v2/"


    fun extractDigitFromUrl(url: String): Int? {
        val regex = Regex("/(\\d+)/$")
        val matchResult = regex.find(url)
        return matchResult?.groupValues?.get(1)?.toInt()
    }

    fun extractPokemonIdFromUrl(url: String): Int? {
        val regex = Regex("""(\d+)\.png$""")
        val matchResult = regex.find(url)

        return matchResult?.groupValues?.get(1)?.toIntOrNull()
    }
}