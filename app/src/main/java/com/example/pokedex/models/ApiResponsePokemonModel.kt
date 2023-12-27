package com.example.pokedex.models

data class ApiResponsePokemonModel(
    val count:Int,
    val next:String,
    val previous:String?,
    val results:List<Pokemon>?
)
