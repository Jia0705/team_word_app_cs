package com.team.wordsapp_casestudy.ui.home

import androidx.lifecycle.ViewModel
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    init {
        getWords()
    }

    // Show ONLY active words on Home
    fun getWords() {
//        _words.update { repo.getActiveWords() }
    }
}