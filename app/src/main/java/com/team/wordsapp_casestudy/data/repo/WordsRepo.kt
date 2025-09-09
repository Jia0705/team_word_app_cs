package com.team.wordsapp_casestudy.data.repo

import com.team.wordsapp_casestudy.data.model.Word

// Sort by title or date
enum class SortBy { TITLE, DATE }

class WordsRepo private constructor() {
    private val words: MutableMap<Int, Word> = mutableMapOf()
    private var counter = 0

    init {
        // generateRandomWord(10)
    }

    private fun nextId(): Int {
        counter += 1
        return counter
    }

    fun addWord(word: Word): Word {
        val id = nextId()
        val saved = word.copy(id = id)
        words[id] = saved
        return saved
    }

    // Basic lists (no search/sort)
    fun getActiveWords(): List<Word> = words.values.filter { !it.isCompleted }
    fun getCompletedWords(): List<Word> = words.values.filter { it.isCompleted }

    fun getWordById(id: Int): Word? = words[id]

    fun updateWord(word: Word) {
        val id = word.id ?: return
        words[id] = word
    }

    fun deleteWord(id: Int) {
        words.remove(id)
    }

    fun markCompleted(id: Int, completed: Boolean = true) {
        val word = words[id] ?: return
        words[id] = word.copy(isCompleted = completed)
    }

    fun generateRandomWord(n: Int) {
        repeat(n) { i ->
            val random = Word(
                title = "Title $i",
                meaning = "Meaning $i",
                synonyms = "Synonyms $i",
                details = "Detail $i",
                isCompleted = false
            )
            addWord(random)
        }
    }


    // Search + Sort
    // Search filter
    private fun filterSearch(list: List<Word>, searchText: String): List<Word> {
        if (searchText.isBlank()) return list
        val text = searchText.lowercase()
        return list.filter {
            it.title.lowercase().contains(text)
        }
    }

    private fun List<Word>.sortedByKey(sortBy: SortBy, ascending: Boolean): List<Word> {
        return when (sortBy) {
            // if sortBy TITLE sort alphabetically
            SortBy.TITLE -> {
                val sorted = this.sortedBy { it.title.lowercase() }
                if (ascending) sorted else sorted.reversed()
            }
            // if sortBy DATE sort by the word’s id
            SortBy.DATE -> {
            // lower id means it was added earlier, so this is like “older first”
                val sorted = this.sortedBy { it.id ?: 0 }      // Oldest → Newest
                if (ascending) sorted else sorted.reversed()   // Newest → Oldest
            }
        }
    }

    // Get active words with search + sort
    fun getActiveWordsFiltered(
        searchText: String,
        ascending: Boolean,
        sortBy: SortBy = SortBy.TITLE
    ): List<Word> {
        var result = getActiveWords()
        result = filterSearch(result, searchText)
        result = result.sortedByKey(sortBy, ascending)
        return result
    }

    // Get completed words with search + sort
    fun getCompletedWordsFiltered(
        searchText: String,
        ascending: Boolean,
        sortBy: SortBy = SortBy.TITLE
    ): List<Word> {
        var result = getCompletedWords()
        result = filterSearch(result, searchText)
        result = result.sortedByKey(sortBy, ascending)
        return result
    }

    companion object {
        private var instance: WordsRepo? = null
        fun getInstance(): WordsRepo {
            if (instance == null) {
                instance = WordsRepo()
            }
            return instance!!
        }
    }
}
