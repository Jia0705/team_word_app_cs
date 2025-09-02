package com.team.wordsapp_casestudy.ui.manage.completed

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.wordsapp_casestudy.databinding.FragmentCompletedBinding
import com.team.wordsapp_casestudy.ui.adapter.WordsAdapter
import kotlinx.coroutines.launch

class CompletedFragment : Fragment() {
    private val viewModel: CompletedViewModel by viewModels()
    private lateinit var binding: FragmentCompletedBinding
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

        adapter = WordsAdapter(emptyList()) { word ->
            val action = CompletedFragmentDirections.actionCompletedFragmentToDetailFragment(word.id!!)
            findNavController().navigate(action)
        }

        binding.rvWords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWords.adapter = adapter

        lifecycleScope.launch {
            viewModel.words.collect { list ->
                adapter.setWords(list)
                binding.llEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWords()
    }
}