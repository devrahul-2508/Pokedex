package com.example.pokedex.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.Type
import com.example.pokedex.utils.Constants
import com.example.pokedex.utils.parseStatToAbbr
import com.example.pokedex.utils.parseStatToColor
import com.example.pokedex.utils.parseTypeToColor
import com.example.pokedex.viewmodels.PokeViewModel
import java.util.Locale
import kotlin.math.round

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 180.dp

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
                .align(Alignment.TopCenter)
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
                .background(MaterialTheme.colorScheme.surface),

            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
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
    modifier: Modifier = Modifier
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
            modifier = Modifier
                .size(36.dp)
                .padding(top = 10.dp, start = 10.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun PokemonDetailStateWrapper(
    pokeMonInfo: Pokemon?,
    modifier: Modifier,
    loadingModifier: Modifier
) {
    if (pokeMonInfo != null) {
        PokemonDetailSection(pokeMonInfo = pokeMonInfo, modifier = modifier.offset(y = (-20).dp))
    } else {
        CircularProgressIndicator(
            modifier = loadingModifier,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun PokemonDetailSection(
    pokeMonInfo: Pokemon,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 140.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "#${pokeMonInfo.id} ${pokeMonInfo.name.capitalize(Locale.ROOT)}",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        PokemonTypeSection(types = pokeMonInfo.types)
        PokemonDetailDataSection(
            pokemonWeight = pokeMonInfo.weight,
            pokemonHeight = pokeMonInfo.height
        )
        PokemonBaseStats(pokeMonInfo)

    }
}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp)
            ) {
                Text(
                    text = type.type.name.capitalize(Locale.ROOT),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    sectionHeight: Dp = 80.dp
) {
    val pokemonWeightInKg = remember {
        round(pokemonWeight * 100f) / 1000f
    }

    val pokemonHeightinMetres = remember {
        round(pokemonHeight * 100f) / 1000f
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg, dataUnit = "kg", dataIcon = painterResource(
                id = androidx.core.R.drawable.ic_call_answer
            ),
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, sectionHeight)
                .background(Color.LightGray)
        )
        PokemonDetailDataItem(
            dataValue = pokemonHeightinMetres, dataUnit = "ms", dataIcon = painterResource(
                id = androidx.core.R.drawable.ic_call_answer_video
            ),
            modifier = Modifier.weight(1f)

        )
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    dataIcon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = dataIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$dataValue$dataUnit", color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f, label = "", animationSpec = tween(
            animDuration,
            animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(currPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(text = statName, fontWeight = FontWeight.Bold)
            Text(
                text = (currPercent.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )

        }


    }
}

@Composable
fun PokemonBaseStats(
    pokemonInfo: Pokemon,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemonInfo.stats.maxOf {
            it.base_stat
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Base Stats",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))

        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]
            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.base_stat,
                statMaxValue = maxBaseStat,
                statColor = parseStatToColor(stat),
                animDelay = i * animDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}