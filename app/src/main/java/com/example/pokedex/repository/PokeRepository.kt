package com.example.pokedex.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.pokedex.api.PokeApi
import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PokeRepository @Inject constructor(private val pokeApi: PokeApi) {

    private val _pokemons = MutableStateFlow<List<Result>>(emptyList())
    val pokemons:StateFlow<List<Result>>
        get() = _pokemons

//    suspend fun getPokemons(){
//        val response = pokeApi.getPokemons("0","20")
//        Log.d("TAG",response.body().toString())
//        if (response.isSuccessful && response.body()!=null)
//
//            _pokemons.emit(response.body()!!.results!!)
//    }

    fun getPokemons() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            PokePagingSource(pokeApi)
        }
    ).flow

    suspend fun getPokemonInfo(name:String,data:MutableStateFlow<Pokemon?>){

        val pokemonInfo = MutableStateFlow<Pokemon?>(null)
        val response = pokeApi.getPokemonInfo(name)

        if(response.isSuccessful && response.body()!=null){
            data.emit(response.body())
        }
    }
}