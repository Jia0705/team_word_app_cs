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

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
            // Word not found → just go back
            findNavController().popBackStack()
            return
        }

        binding.tvTitle.text = word.title
        binding.tvMeaning.text = word.meaning
        binding.tvSynonym.text = word.synonyms
        binding.tvDetails.text = word.details

        // Done / Unmark
        binding.btnDone.text =
            if (word.isCompleted) getString(R.string.unmark) else getString(R.string.done)

        // Done or Unmark → update + notify + return to the list we came from
        binding.btnDone.setOnClickListener {
            val isCompletedNow = viewModel.getWordById(wordId)?.isCompleted == true
            if (isCompletedNow) {
                // Unmark -> move to Home (visible on Home)
                viewModel.markCompleted(wordId, completed = false)
            } else {
                // Done -> move to Completed (disappear from Home)
                viewModel.markCompleted(wordId)
            }
            setFragmentResult("manage_word", Bundle().apply { putBoolean("refresh", true) })
            // go back to the previous screen
            findNavController().popBackStack()
        }

        // Update → Edit
        binding.btnUpdate.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToEditWordFragment(wordId)
            findNavController().navigate(action)
        }

        // Delete → notify + back to previous list
        binding.btnDelete.setOnClickListener {
            viewModel.deleteWord(wordId)
            setFragmentResult("manage_word", Bundle().apply { putBoolean("refresh", true) })
            findNavController().popBackStack()
        }
    }
}
