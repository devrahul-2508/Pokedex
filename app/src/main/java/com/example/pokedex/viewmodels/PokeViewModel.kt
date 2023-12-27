package com.example.pokedex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.models.Pokemon
import com.example.pokedex.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(private val pokeRepository: PokeRepository):ViewModel(){

    val pokemons:StateFlow<List<Pokemon>>
        get() = pokeRepository.pokemons

    init {
        viewModelScope.launch {
            pokeRepository.getPokemons()
        }
    }
}