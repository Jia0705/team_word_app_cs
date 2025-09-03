package com.team.wordsapp_casestudy.ui.manage.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.team.wordsapp_casestudy.R
import com.team.wordsapp_casestudy.databinding.FragmentBaseManageWordBinding
import kotlinx.coroutines.launch

abstract class BaseManageWordFragment : Fragment() {

    protected abstract val viewModel: BaseManageWordViewModel
    protected lateinit var binding: FragmentBaseManageWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseManageWordBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.finish.collect {
                // refresh
                setFragmentResult("manage_word", Bundle())

                // get the navigation controller so we can move between screens
                val nav = findNavController()

                // try to jump back past the detail screen
                // if the stack is: Home → Detail → Edit, this will remove both Detail and Edit, leaving user at Home
                // switch is true if it worked, false if there was no Detail in the stack
                val switch = nav.popBackStack(R.id.detailFragment, true)

                // If there was no Detail screen (for example, you came from Add instead of Edit),
                // then just go back one step which is home
                if (!switch) {
                    nav.popBackStack()
                }
            }
        }
    }
}
