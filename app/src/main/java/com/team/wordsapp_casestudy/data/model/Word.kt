package com.team.wordsapp_casestudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word (
    @PrimaryKey(true)
    val id: Int? = null,
    val title: String,
    val meaning: String,
    val synonyms: String,
    val details: String,
    val isCompleted: Boolean = false
)