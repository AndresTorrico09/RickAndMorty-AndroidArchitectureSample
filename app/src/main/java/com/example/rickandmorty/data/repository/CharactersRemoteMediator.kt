package com.example.rickandmorty.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.entities.RemoteKey
import com.example.rickandmorty.data.local.AppDatabase
import com.example.rickandmorty.data.remote.CharacterRemoteDataSource

private const val CHARACTER_STARTING_PAGE_INDEX = 1

@SuppressLint("LogNotTimber")
@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Character>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextKey?.minus(1) ?: CHARACTER_STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    nextKey
                }
            }

            val response = remoteDataSource.getCharactersByPage(page)
            val characters = response.data?.results!!
            val endPaginationReach = characters.isEmpty()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeysDao().clearRemoteKeys()
                    appDatabase.characterDao().clearCharacters()
                }

                val prevkey = if (page == CHARACTER_STARTING_PAGE_INDEX) null else page.minus(1)
                val nextKey = if (endPaginationReach) null else page.plus(1)
                val keys = characters.map {
                    RemoteKey(id = it.id, prevKey = prevkey, nextKey = nextKey)
                }

                appDatabase.remoteKeysDao().insertAll(keys)
                appDatabase.characterDao().insertAll(characters)
            }

            return MediatorResult.Success(endOfPaginationReached = endPaginationReach)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): RemoteKey? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                appDatabase.remoteKeysDao().remoteKeysCharacterId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): RemoteKey? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                appDatabase.remoteKeysDao().remoteKeysCharacterId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): RemoteKey? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                appDatabase.remoteKeysDao().remoteKeysCharacterId(repoId)
            }
        }
    }

}