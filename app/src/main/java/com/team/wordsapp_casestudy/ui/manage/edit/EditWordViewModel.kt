package com.team.wordsapp_casestudy.ui.manage.edit

import androidx.lifecycle.viewModelScope
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.ui.manage.base.BaseManageWordViewModel
import kotlinx.coroutines.launch

class EditWordViewModel : BaseManageWordViewModel() {

    private var word: Word? = null

    fun getWord(id: Int) {
        repo.getWordById(id)?.let {
            word = it
            title.value = it.title
            meaning.value = it.meaning
            synomyn.value = it.synonyms
            detail.value = it.details
        }
    }
    override fun sumbit() {
        word?.let {
            repo.updateWord(
                it.copy(
                    title =title.value,
                    meaning = meaning.value,
                    synonyms = synomyn.value,
                    details = detail.value
                )
            )
        }

        viewModelScope.launch {
            _finish.emit(Unit)
        }
    }
}