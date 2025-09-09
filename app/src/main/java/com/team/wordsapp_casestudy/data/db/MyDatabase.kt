package com.team.wordsapp_casestudy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.team.wordsapp_casestudy.data.model.Word

@Database(entities = [Word::class], version = 1)
abstract class MyDatabase: RoomDatabase() {

    abstract fun getWordsDao(): WordsDao

    companion object {
        const val NAME = "my_database"
    }
}