package com.example.rickandmorty.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.local.AppDatabase
import com.example.rickandmorty.data.local.CharacterDao
import com.example.rickandmorty.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty.utils.CoroutinesDispatcherProvider
import com.example.rickandmorty.utils.performGetOperation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val appDatabase: AppDatabase
) {
    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
        enablePlaceholders = false
    )

    fun getCharacter(id: Int) = performGetOperation(
        databaseQuery = { appDatabase.characterDao().getCharacter(id) },
        networkCall = { remoteDataSource.getCharacter(id) },
        saveCallResult = { appDatabase.characterDao().insert(it) }
    )

    fun getCharacters(): Flow<PagingData<Character>> {
        val pagingSourceFactory = { appDatabase.characterDao().getAllCharacters() }
        val remoteMediator = CharactersRemoteMediator (remoteDataSource, appDatabase)

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = pagingConfig,
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object{
        private const val PAGE_SIZE = 10
    }
}