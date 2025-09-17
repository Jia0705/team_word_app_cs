package com.team.wordsapp_casestudy.ui.manage.add

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.team.wordsapp_casestudy.MyApp
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.data.repo.WordsRepo
import com.team.wordsapp_casestudy.ui.manage.base.BaseManageWordViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AddWordViewModel(repo: WordsRepo) : BaseManageWordViewModel(repo) {
    override fun submit() {
        try {
            require(title.value.isNotBlank()) {"Title cannot be blank"}
            val word = Word(
                title = title.value,
                meaning = meaning.value,
                synonyms = synomyn.value,
                details = detail.value
            )
            viewModelScope.launch (Dispatchers.IO){
                repo.addWord(word)
                _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch { _error.emit(e.message.toString()) }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                AddWordViewModel(repo = myRepository)
            }
        }
    }
}