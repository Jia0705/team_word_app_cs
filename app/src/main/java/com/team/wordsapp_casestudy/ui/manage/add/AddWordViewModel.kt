package com.team.wordsapp_casestudy.ui.manage.add

import androidx.lifecycle.viewModelScope
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.ui.manage.base.BaseManageWordViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class AddWordViewModel : BaseManageWordViewModel() {
    override fun submit() {
        try {
            require(title.value.isNotBlank()) {"Title cannot be blank"}
            val word = Word(
                title = title.value,
                meaning = meaning.value,
                synonyms = synomyn.value,
                details = detail.value
            )
            repo.addWord(word)
            viewModelScope.launch {
                _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch { _error.emit(e.message.toString()) }
        }
    }
}