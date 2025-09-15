package com.team.wordsapp_casestudy.ui.manage.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.team.wordsapp_casestudy.MyApp
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.data.repo.WordsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repo: WordsRepo
) : ViewModel() {

    private val _word = MutableStateFlow<Word?>(null)
    val word: StateFlow<Word?> = _word
    fun getWordById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _word.value = repo.getWordById(id)
        }
    }

    // Mark done or unmark
    fun markCompleted(id: Int, completed: Boolean = true) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.markCompleted(id, completed)
        }
    }

    fun deleteWord(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteWord(id)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                DetailViewModel(repo = myRepository)
            }
        }
    }
}
