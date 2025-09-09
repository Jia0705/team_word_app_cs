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

    @Query("SElECT * FROM word WHERE id = :id")
    fun getWordById(id: Int): Word?

    @Insert
    fun addWord(word: Word)

    @Update
    fun update(word: Word)

    @Query("DELETE FROM word WHERE id = :id")
    fun delete(id: Int)
}