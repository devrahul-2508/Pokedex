package com.example.pokedex.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.models.Pokemon
import com.example.pokedex.utils.Constants
import com.example.pokedex.viewmodels.PokeViewModel


@Composable
fun PokemonScreen() {
    val pokeViewModel: PokeViewModel = hiltViewModel()
    val pokemons = pokeViewModel.getPokemons().collectAsLazyPagingItems()
    val colors = listOf(
        Color.Blue, // Start color
        Color.Magenta // End color
    )

    // Create a vertical gradient from top to bottom
    val verticalGradientBrush = Brush.verticalGradient(
        colors = colors,
        startY = 0f,
        endY = 500f // Adjust the end position as needed
    )

    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(brush = verticalGradientBrush)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Drawable Image",
                modifier = Modifier.size(200.dp) // Adjust size as needed
            )


                LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(8.dp)) {
                   items(pokemons.itemCount){index->
                       PokemonItem(pokemon = pokemons[index]!!)

                   }
            }
        }
    }


}

@Composable
fun PokemonItem(pokemon: Pokemon) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color.White)
            .aspectRatio(1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.size(120.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            "https://unpkg.com/pokeapi-sprites@2.0.2/sprites/pokemon/other/dream-world/${
                                Constants.extractDigitFromUrl(
                                    pokemon.url
                                )
                            }.svg"
                        )
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                )
            }
            Box(modifier = Modifier.height(5.dp))
            Text(
                text = pokemon.name, style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                )
            )
        }
    }
}