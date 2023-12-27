package com.example.pokedex.api

import com.example.pokedex.models.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PokeApi {

    @GET("/pokemon")
    suspend fun getPokemons(
        @Query("offset") offset:String,
        @Query("limit") limit:String
    ):Response<List<Pokemon>>
}