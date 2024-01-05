package com.example.pokedex.screens

import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokedex.models.Pokemon
import com.example.pokedex.utils.Constants
import com.example.pokedex.viewmodels.PokeViewModel

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 40.dp,
    pokemonImageSize: Dp = 200.dp

) {

    val pokeviewModel = hiltViewModel<PokeViewModel>()
    pokeviewModel.getPokemonInfo(pokemonName)
    val pokemonDetails = pokeviewModel.pokemonInfo.collectAsState()

    Log.d("BAM", pokemonDetails.value.toString())



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .padding(bottom = 16.dp)
    ) {

        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
        )
        PokemonDetailStateWrapper(
            pokeMonInfo = pokemonDetails.value,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
        )
        Box(

            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding)
                .align(Alignment.TopCenter)
        ) {
            if (pokemonDetails.value != null) {

                pokemonDetails.value?.sprites.let {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                "https://unpkg.com/pokeapi-sprites@2.0.2/sprites/pokemon/other/dream-world/${
                                    Constants.extractPokemonIdFromUrl(
                                        it!!.front_default
                                    )
                                }.svg"
                            )
                            .crossfade(true)
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(pokemonImageSize)
                            .padding(top = topPadding)
                            .align(Alignment.TopCenter)
                    )
                }

            }
        }
    }

}

@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier.background(
            Brush.verticalGradient(
                listOf(
                    Color.Black,
                    Color.Transparent
                )

            )
        )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = modifier
                .size(36.dp)
                .align(Alignment.TopStart)

                .offset(16.dp, 16.dp)

                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun PokemonDetailStateWrapper(
    pokeMonInfo: Pokemon?,
    modifier: Modifier
) {
    if (pokeMonInfo != null) {

    } else {
        Box(
            modifier = Modifier.
            size(100.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}