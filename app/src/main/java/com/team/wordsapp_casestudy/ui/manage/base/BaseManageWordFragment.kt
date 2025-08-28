package com.team.wordsapp_casestudy.ui.manage.base

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.wordsapp_casestudy.R

class BaseManageWordFragment : Fragment() {

    private val viewModel: BaseManageWordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_base_manage_word, container, false)
    }
}