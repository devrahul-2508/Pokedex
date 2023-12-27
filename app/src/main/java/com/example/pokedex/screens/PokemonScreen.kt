package com.example.pokedex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedex.models.Pokemon
import com.example.pokedex.viewmodels.PokeViewModel



@Composable
fun PokemonScreen() {
    val pokeViewModel: PokeViewModel = hiltViewModel()
    val pokemons: State<List<Pokemon>> = pokeViewModel.pokemons.collectAsState()

    LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(8.dp)) {
        items(pokemons.value) {pokemon->
            PokemonItem(pokemon)
        }
    }


}

@Composable
fun PokemonItem(pokemon: Pokemon) {
    Box(
        modifier = Modifier
            .padding(8.dp)

            .background(color = Color.White)
            .clickable {

            }
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "https://i.pinimg.com/550x/cb/33/49/cb3349b86ca661ca61ae9a36d88d70d4.jpg",
                contentDescription = null,
            )
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