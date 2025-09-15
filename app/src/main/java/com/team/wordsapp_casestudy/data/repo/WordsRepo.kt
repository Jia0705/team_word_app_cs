package com.team.wordsapp_casestudy.data.repo

import com.team.wordsapp_casestudy.data.db.WordsDao
import com.team.wordsapp_casestudy.data.model.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Sort by title or date (DATE = by id order)
enum class SortBy { TITLE, DATE }

class WordsRepo(
    private val dao: WordsDao
) {
    fun getAllWords(): Flow<List<Word>> {
        return dao.getAllWords()
    }
    suspend fun getWordById(id: Int): Word? {
        return dao.getWordById(id)
    }

    suspend fun addWord(word: Word) {
        dao.addWord(word)
    }

    suspend fun updateWord(word: Word) {
        dao.update(word)
    }

    suspend fun deleteWord(id: Int) {
        dao.delete(id)
    }

    suspend fun markCompleted(id: Int, completed: Boolean) {
        val word = dao.getWordById(id) ?: return
        dao.update(word.copy(isCompleted = completed))
    }

    // Search + Sort
    private fun filterSearch(list: List<Word>, searchText: String): List<Word> {
        if (searchText.isBlank()) return list
        val text = searchText.lowercase()
        return list.filter { it.title.lowercase().contains(text) }
    }

    private fun List<Word>.sortedByKey(sortBy: SortBy, ascending: Boolean): List<Word> =
        when (sortBy) {
            SortBy.TITLE -> {
                val sorted = this.sortedBy { it.title.lowercase() }
                if (ascending) sorted else sorted.reversed()
            }
            SortBy.DATE -> {
                val sorted = this.sortedBy { it.id }
                if (ascending) sorted else sorted.reversed()
            }
        }

    fun getActiveWordsFiltered(
        searchText: String,
        ascending: Boolean,
        sortBy: SortBy = SortBy.TITLE
    ): Flow<List<Word>> {
        return dao.getAllWords().map { list ->
            var result = list.filter { !it.isCompleted }
            result = filterSearch(result, searchText)
            result = result.sortedByKey(sortBy, ascending)
            result
        }
    }

    fun getCompletedWordsFiltered(
        searchText: String,
        ascending: Boolean,
        sortBy: SortBy = SortBy.TITLE
    ): Flow<List<Word>> {
        return dao.getAllWords().map { list ->
            var result = list.filter { it.isCompleted }
            result = filterSearch(result, searchText)
            result = result.sortedByKey(sortBy, ascending)
            result
        }
    }
}

