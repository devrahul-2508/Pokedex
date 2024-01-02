package com.example.pokedex.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokedex.api.PokeApi
import com.example.pokedex.models.Result
import javax.inject.Inject

class PokePagingSource @Inject constructor(private val pokeApi: PokeApi):
    PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: 0
            val response = pokeApi.getPokemons(page,20)

            LoadResult.Page(
                data = response.body()?.results!!,
                prevKey = if (page == 0) null else page.minus(20),
                nextKey = if (response.body()?.results!!.isEmpty()) null else page.plus(20),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}