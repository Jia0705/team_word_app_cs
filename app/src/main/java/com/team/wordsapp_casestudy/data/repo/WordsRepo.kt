package com.team.wordsapp_casestudy.data.repo

import com.team.wordsapp_casestudy.data.model.Word

class WordsRepo private constructor(){

    val words: MutableMap<Int, Word> = mutableMapOf()

    var counter = 0

    init {
        generateRandomWord(5)
    }

    fun addWord(word: Word) {
        counter++
        words[counter] = word.copy(id = counter)
    }

    fun getAllWord() = words.values.toList()

    fun getWordById(id: Int) = words[id]

    fun deleteWord(id: Int) {
        words.remove(id)
    }

    fun updateWord(id: Int, word: Word) {
        words[id] = word
    }

    fun generateRandomWord(n: Int) {
        repeat(n) {
            words[++counter] = Word(
                title = "Title $it",
                meaning = "Meaning $it",
                synonyms = "Synonyms $it",
                details = "Detail $it"
            )
        }
    }

    companion object {
        private var instance: WordsRepo? = null

        fun getInstance(): WordsRepo {
            if(instance == null) {
                instance = WordsRepo()
            }
            return instance!!
        }
    }
}