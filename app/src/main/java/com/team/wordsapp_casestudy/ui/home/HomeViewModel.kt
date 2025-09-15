package com.team.wordsapp_casestudy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.team.wordsapp_casestudy.MyApp
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.data.repo.SortBy
import com.team.wordsapp_casestudy.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: WordsRepo
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
    private fun getWords() {
        viewModelScope.launch {
            repo.getActiveWordsFiltered(searchText, isAscending, sortBy).collect { list ->
                _words.value = list
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                HomeViewModel(repo = myRepository)
            }
        }
    }
}
