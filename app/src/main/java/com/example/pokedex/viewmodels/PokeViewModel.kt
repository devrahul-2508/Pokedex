package com.example.pokedex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokedex.models.Result
import com.example.pokedex.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(private val pokeRepository: PokeRepository):ViewModel(){


    fun getPokemons(): Flow<PagingData<Result>> = pokeRepository.getPokemons().cachedIn(viewModelScope)

}