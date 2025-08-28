package com.team.wordsapp_casestudy.data.model

data class Word (
    val id: Int? = null,
    val title: String,
    val meaning: String,
    val synonyms: String,
    val details: String,
    val isCompleted: Boolean = false
)