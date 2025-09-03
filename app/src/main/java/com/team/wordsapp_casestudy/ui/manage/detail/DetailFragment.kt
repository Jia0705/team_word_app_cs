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
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.databinding.FragmentDetailBinding
import com.team.wordsapp_casestudy.ui.manage.popup.CompletePopFragment
import com.team.wordsapp_casestudy.ui.manage.popup.DeletePopFragment

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
            showConfirmDialog(word)
        }

        // Update → Edit
        binding.btnUpdate.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToEditWordFragment(wordId)
            findNavController().navigate(action)
        }

        // Delete → notify + back to previous list
        binding.btnDelete.setOnClickListener {
            showDeleteDialog(word)
        }
    }

    private fun showDeleteDialog(word: Word) {
        val deleteDialog = DeletePopFragment().apply {
            setListener(object: DeletePopFragment.Listener {
                override fun onClickDelete() {
                    viewModel.deleteWord(word.id!!)
                    setFragmentResult("manage_word", Bundle().apply { putBoolean("refresh", true) })
                    findNavController().popBackStack()
                }

                override fun onClickCancel() {

                }
            })
        }
        deleteDialog.show(parentFragmentManager, "DeletePopFragment")
    }

    private fun showConfirmDialog(word: Word) {
        val confirmDialog = CompletePopFragment().apply {
            setListener(object : CompletePopFragment.Listener {
                override fun onClickYes() {
                    val isCompletedNow = viewModel.getWordById(word.id!!)?.isCompleted == true
                    if (isCompletedNow) {
                        // Unmark -> move to Home (visible on Home)
                        viewModel.markCompleted(word.id, completed = false)
                    } else {
                        // Done -> move to Completed (disappear from Home)
                        viewModel.markCompleted(word.id)
                    }
                    setFragmentResult("manage_word", Bundle().apply { putBoolean("refresh", true) })
                    // go back to the previous screen
                    findNavController().popBackStack()
                }

                override fun onClickNo() {

                }
            })
        }
        confirmDialog.show(parentFragmentManager, "CompletePopFragment")
    }
}
