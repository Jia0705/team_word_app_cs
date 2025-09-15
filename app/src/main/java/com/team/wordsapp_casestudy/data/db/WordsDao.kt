package com.team.wordsapp_casestudy.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.team.wordsapp_casestudy.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {
    @Query("SELECT * FROM word")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE id = :id")
    suspend fun getWordById(id: Int): Word?

    @Insert
    suspend fun addWord(word: Word)

    @Update
    suspend fun update(word: Word)

    @Query("DELETE FROM word WHERE id = :id")
    suspend fun delete(id: Int)
}