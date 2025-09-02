package com.team.wordsapp_casestudy.ui.manage.edit

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.team.wordsapp_casestudy.R
import com.team.wordsapp_casestudy.ui.manage.base.BaseManageWordFragment
import kotlin.getValue

class EditWordFragment : BaseManageWordFragment() {

    override val viewModel: EditWordViewModel by viewModels()

    private val args: EditWordFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.getWord(args.wordId)
        binding.tvHeader.setText(R.string.update)
        binding.mbSubmit.setText(R.string.update)
    }
}