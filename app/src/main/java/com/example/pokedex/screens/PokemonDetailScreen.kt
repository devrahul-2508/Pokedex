package com.example.pokedex.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokedex.viewmodels.PokeViewModel

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp

) {

    val pokeviewModel = hiltViewModel<PokeViewModel>()

    val pokemonDetails = pokeviewModel.getPokemonInfo(pokemonName).collectAsState()

    Log.d("BAM", pokemonDetails.value.toString())

}