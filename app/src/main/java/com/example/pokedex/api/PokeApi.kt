package com.example.pokedex.api

import com.example.pokedex.models.ApiResponsePokemonModel
import com.example.pokedex.models.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("offset") offset:Int,
        @Query("limit") limit:Int
    ):Response<ApiResponsePokemonModel>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name:String
    ):Response<Pokemon>
}