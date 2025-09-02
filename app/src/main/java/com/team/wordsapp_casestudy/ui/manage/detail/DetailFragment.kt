package com.team.wordsapp_casestudy.ui.manage.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.team.wordsapp_casestudy.R
import com.team.wordsapp_casestudy.databinding.FragmentDetailBinding
import kotlin.getValue

class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wordId = args.wordId
        val word = viewModel.getWordById(wordId)

        if (word == null) {
            findNavController().popBackStack()
            return
        }

        binding.tvTitle.text = word.title
        binding.tvMeaning.text = word.meaning
        binding.tvSynonym.text = word.synonyms
        binding.tvDetails.text = word.details

        // Done and unmark
        binding.btnDone.text =
            if (word.isCompleted) getString(R.string.unmark) else getString(R.string.done)

        // check if is completed and back to home
        binding.btnDone.setOnClickListener {
            val isCompletedNow = viewModel.getWordById(wordId)?.isCompleted == true
            if (isCompletedNow) {
                // Unmark -> make active (visible on Home)
                viewModel.markCompleted(wordId, completed = false)
            } else {
                // Done -> move to Completed (disappear from Home)
                viewModel.markCompleted(wordId)
            }
            setFragmentResult("manage_word", Bundle().apply { putBoolean("refresh", true) })
            findNavController().popBackStack(R.id.homeFragment, false)
        }

        // Update -> Edit
        binding.btnUpdate.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToEditWordFragment(wordId)
            findNavController().navigate(action)
        }

        // Delete -> Home
        binding.btnDelete.setOnClickListener {
            viewModel.deleteWord(wordId)
            setFragmentResult("manage_word", Bundle().apply { putBoolean("refresh", true) })
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }
}