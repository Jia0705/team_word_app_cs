package com.team.wordsapp_casestudy.ui.manage.add

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import com.team.wordsapp_casestudy.R
import com.team.wordsapp_casestudy.ui.manage.base.BaseManageWordFragment

class AddWordFragment : BaseManageWordFragment() {

    override val viewModel: AddWordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.tbTitle.setTitle("Add Word")
        binding.mbSubmit.setText(R.string.add_new)
    }
}