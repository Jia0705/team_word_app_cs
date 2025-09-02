package com.team.wordsapp_casestudy.ui.manage.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.team.wordsapp_casestudy.databinding.FragmentBaseManageWordBinding
import kotlinx.coroutines.launch

abstract class BaseManageWordFragment : Fragment() {

    protected abstract val viewModel: BaseManageWordViewModel

    protected lateinit var binding: FragmentBaseManageWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseManageWordBinding.inflate(
            layoutInflater,
            container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.finish.collect {
                setFragmentResult("manage_word", Bundle())
                findNavController().popBackStack()
            }
        }
    }
}