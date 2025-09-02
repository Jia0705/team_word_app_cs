package com.team.wordsapp_casestudy.ui.manage.completed

import androidx.lifecycle.ViewModel
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CompletedViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    init {
        getWords()
    }

    fun getWords() {
        _words.update { repo.getCompletedWords() }
    }
}