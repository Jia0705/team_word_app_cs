package com.team.wordsapp_casestudy.ui.manage.completed

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.wordsapp_casestudy.R

class CompletedFragment : Fragment() {

    private val viewModel: CompletedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_completed, container, false)
    }
}