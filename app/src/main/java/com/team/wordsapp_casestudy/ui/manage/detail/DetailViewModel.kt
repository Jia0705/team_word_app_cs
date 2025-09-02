package com.team.wordsapp_casestudy.ui.manage.detail

import androidx.lifecycle.ViewModel
import com.team.wordsapp_casestudy.data.repo.WordsRepo

class DetailViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {

    fun getWordById(id: Int) = repo.getWordById(id)

    // Mark done or unmark
    fun markCompleted(id: Int, completed: Boolean = true) =
        repo.markCompleted(id, completed)

    fun deleteWord(id: Int) = repo.deleteWord(id)
}
