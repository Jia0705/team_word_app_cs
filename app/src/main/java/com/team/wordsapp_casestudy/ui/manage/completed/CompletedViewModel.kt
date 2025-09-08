package com.team.wordsapp_casestudy.ui.manage.completed

import androidx.lifecycle.ViewModel
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.data.repo.SortBy
import com.team.wordsapp_casestudy.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CompletedViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words
    private var searchText: String = ""
    private var isAscending: Boolean = true
    private var sortBy: SortBy = SortBy.TITLE

    fun setSearchText(text: String) {
        searchText = text
        getWords()
    }

    fun setSortAscending(value: Boolean) {
        isAscending = value
        getWords()
    }

    fun setSortByTitle(isTitle: Boolean) {
        sortBy = if (isTitle) SortBy.TITLE else SortBy.DATE
        getWords()
    }

    fun getWords() {
        _words.value = repo.getCompletedWordsFiltered(searchText, isAscending, sortBy)
    }
}