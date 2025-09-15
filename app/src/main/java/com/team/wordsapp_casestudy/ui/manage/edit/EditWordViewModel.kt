package com.team.wordsapp_casestudy.ui.manage.edit

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

class EditWordViewModel(repo: WordsRepo) : BaseManageWordViewModel(repo) {
    private var word: Word? = null

    fun getWord(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getWordById(id)?.let {
                word = it
                title.value = it.title
                meaning.value = it.meaning
                synomyn.value = it.synonyms
                detail.value = it.details
            }
        }
    }

    override fun submit() {
        viewModelScope.launch (Dispatchers.IO){
            try {
                require(title.value.isNotBlank()) {"Title cannot be blank"}
                word?.let {
                    repo.updateWord(
                        it.copy(
                            title = title.value,
                            meaning = meaning.value,
                            synonyms = synomyn.value,
                            details = detail.value
                        )
                    )
                }
                _finish.emit(Unit)
            } catch (e: Exception) {
                _error.emit(e.message.toString())
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                EditWordViewModel(repo = myRepository)
            }
        }
    }
}