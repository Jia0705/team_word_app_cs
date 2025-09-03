package com.team.wordsapp_casestudy.ui.manage.base

import androidx.lifecycle.ViewModel
import com.team.wordsapp_casestudy.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseManageWordViewModel(
    protected val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {

    val title = MutableStateFlow("")

    val meaning = MutableStateFlow("")

    val synomyn = MutableStateFlow("")

    val detail = MutableStateFlow("")

    protected val _finish = MutableSharedFlow<Unit>()

    val finish = _finish.asSharedFlow()

    abstract fun submit()
}