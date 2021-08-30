package com.example.rickandmorty.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.local.CharacterDao
import com.example.rickandmorty.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty.utils.CoroutinesDispatcherProvider
import com.example.rickandmorty.utils.performGetOperation
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) {
    private val pagedListConfig = PagedList.Config.Builder()
        .setPrefetchDistance(10)
        .setPageSize(20).build()

    private val boundaryCallback =
        CharactersBoundaryCallback(remoteDataSource, localDataSource, coroutinesDispatcherProvider)

    fun getCharacter(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getCharacter(id) },
        networkCall = { remoteDataSource.getCharacter(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getCharacters(): LiveData<PagedList<Character>> = LivePagedListBuilder(
        localDataSource.getAllCharacters(),
        pagedListConfig
    ).setBoundaryCallback(boundaryCallback).build()
}