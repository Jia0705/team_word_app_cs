package com.team.wordsapp_casestudy.ui.manage.add

import androidx.lifecycle.viewModelScope
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.ui.manage.base.BaseManageWordViewModel
import kotlinx.coroutines.launch

class AddWordViewModel : BaseManageWordViewModel() {
    override fun sumbit() {
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
    }
}