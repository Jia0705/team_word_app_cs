package com.team.wordsapp_casestudy.ui.manage.completed

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.wordsapp_casestudy.databinding.FragmentCompletedBinding
import com.team.wordsapp_casestudy.ui.adapter.WordsAdapter
import com.team.wordsapp_casestudy.ui.manage.popup.SortPopFragment
import kotlinx.coroutines.launch

class CompletedFragment : Fragment() {
    private lateinit var binding: FragmentCompletedBinding
    private val viewModel: CompletedViewModel by viewModels{
        CompletedViewModel.Factory
    }
    private lateinit var adapter: WordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        lifecycleScope.launch {
            viewModel.words.collect {
                adapter.setWords(it)
                binding.llEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        setFragmentResultListener("manage_word") { _, _ ->
            parentFragmentManager.clearFragmentResult("manage_word")
        }

        // search
        binding.etSearch.addTextChangedListener { text ->
            viewModel.setSearchText(text?.toString().orEmpty())
        }

        // sort
        binding.btnSort.setOnClickListener {
            showSortDialog()
        }
    }

    fun setupAdapter() {
        adapter = WordsAdapter(emptyList()) {
            val action = CompletedFragmentDirections.actionCompletedFragmentToDetailFragment(it.id)
            findNavController().navigate(action)
        }
        binding.rvWords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWords.adapter = adapter
    }
    private fun showSortDialog() {
        val sortDialog = SortPopFragment().apply {
            setListener(object : SortPopFragment.Listener {
                override fun onClickDone() {}

                override fun onSortBySelected(isTitle: Boolean) {
                    viewModel.setSortByTitle(isTitle)
                }

                override fun onSortOrderSelected(isAscending: Boolean) {
                    viewModel.setSortAscending(isAscending)
                }
            })
        }
        sortDialog.show(parentFragmentManager, "SortPopFragment")
    }
}