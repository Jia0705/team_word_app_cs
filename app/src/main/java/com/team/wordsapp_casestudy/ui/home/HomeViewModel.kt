package com.team.wordsapp_casestudy.ui.home

import androidx.lifecycle.ViewModel
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.data.repo.SortBy
import com.team.wordsapp_casestudy.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words
    private var searchText: String = ""
    private var isAscending: Boolean = true  // true = A–Z, false = Z–A
    private var sortBy: SortBy = SortBy.TITLE

    init {
        getWords() // basically is refresh
    }

    // search
    fun setSearchText(text: String) {
        searchText = text
        getWords()
    }

    fun setSortAscending(value: Boolean) {
        isAscending = value
        getWords()
    }
    // sort title
    fun setSortByTitle(isTitle: Boolean) {
        sortBy = if (isTitle) SortBy.TITLE else SortBy.DATE
        getWords()
    }

    // Show ONLY active words
    fun getWords() {
        _words.value = repo.getActiveWordsFiltered(searchText, isAscending, sortBy)
        //  "no filtering" use:
        // _words.value = repo.getActiveWords()
    }
}
