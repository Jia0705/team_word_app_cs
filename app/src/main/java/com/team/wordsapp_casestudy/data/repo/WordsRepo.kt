package com.team.wordsapp_casestudy.data.repo

import com.team.wordsapp_casestudy.data.model.Word

class WordsRepo private constructor() {
    private val words: MutableMap<Int, Word> = mutableMapOf()
    private var counter = 0

    init {
//        generateRandomWord(10)
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

    fun getWords(): List<Word> = words.values.toList()

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
        val com = words[id] ?: return
        words[id] = com.copy(isCompleted = completed)
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
