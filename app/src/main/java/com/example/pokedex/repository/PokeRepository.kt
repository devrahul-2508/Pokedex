package com.example.pokedex.repository

import android.util.Log
import com.example.pokedex.api.PokeApi
import com.example.pokedex.models.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PokeRepository @Inject constructor(private val pokeApi: PokeApi) {

    private val _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons:StateFlow<List<Pokemon>>
        get() = _pokemons

    suspend fun getPokemons(){
        val response = pokeApi.getPokemons("0","20")
        Log.d("TAG",response.body().toString())
        if (response.isSuccessful && response.body()!=null)

            _pokemons.emit(response.body()!!.results!!)
    }
}