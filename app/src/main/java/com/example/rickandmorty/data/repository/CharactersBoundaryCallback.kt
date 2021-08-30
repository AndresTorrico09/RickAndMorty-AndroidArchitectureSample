package com.example.rickandmorty.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PagedList
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.local.CharacterDao
import com.example.rickandmorty.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty.utils.CoroutinesDispatcherProvider
import com.example.rickandmorty.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

@SuppressLint("LogNotTimber")
class CharactersBoundaryCallback(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : PagedList.BoundaryCallback<Character>() {

    private var requestedPage = 1
    private var isRequestedRunning = AtomicBoolean(false)
    private val ioCoroutineScope by lazy {
        CoroutineScope(coroutinesDispatcherProvider.io)
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        Log.v("boundaryy", "onZeroItemsLoaded")

        fetchAndStoreCharacters()
    }

    override fun onItemAtFrontLoaded(itemAtFront: Character) {
        Log.v("boundaryy", "onItemAtFrontLoaded")
    }

    override fun onItemAtEndLoaded(itemAtEnd: Character) {
        super.onItemAtEndLoaded(itemAtEnd)
        Log.v("boundaryy", "onItemAtEndLoaded")

        fetchAndStoreCharacters()
    }

    private fun fetchAndStoreCharacters() {
        if (isRequestedRunning.get()) return

        ioCoroutineScope.launch {
            isRequestedRunning.set(true)
            val response = remoteDataSource.getCharactersByPage(requestedPage)

            when (response.status) {
                Resource.Status.SUCCESS -> {
                    val results = response.data?.results!!
                    if (results.isNotEmpty())
                        localDataSource.insertAll(results)

                    requestedPage++
                    isRequestedRunning.set(false)
                }
                Resource.Status.ERROR -> {
                    isRequestedRunning.set(false)
                }
                Resource.Status.LOADING -> TODO()
            }
        }
    }

    fun invalidate() {
        ioCoroutineScope.cancel()
    }

}