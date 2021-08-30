package com.example.rickandmorty.data.remote

import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val characterService: CharacterService
): BaseDataSource() {

    suspend fun getAllCharacters() = getResult { characterService.getAllCharacters() }
    suspend fun getCharactersByPage(page: Int) = getResult { characterService.getCharactersByPage(page) }
    suspend fun getCharacter(id: Int) = getResult { characterService.getCharacter(id) }
}