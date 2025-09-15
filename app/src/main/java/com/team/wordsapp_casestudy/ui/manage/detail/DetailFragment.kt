package com.team.wordsapp_casestudy.ui.manage.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.team.wordsapp_casestudy.R
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.databinding.FragmentDetailBinding
import com.team.wordsapp_casestudy.ui.manage.popup.CompletePopFragment
import com.team.wordsapp_casestudy.ui.manage.popup.DeletePopFragment
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.Factory
    }
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
        viewModel.getWordById(wordId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.word.collect { word ->
                if (word == null) {
                    return@collect
                }

                binding.tvTitle.text = word.title
                binding.tvMeaning.text = word.meaning
                binding.tvSynonym.text = word.synonyms
                binding.tvDetails.text = word.details

                // Done / Unmark
                binding.btnDone.text =
                    if (word.isCompleted) getString(R.string.unmark) else getString(R.string.done)

                // Done or Unmark
                binding.btnDone.setOnClickListener {
                    showConfirmDialog(word)
                }

                // Update → Edit
                binding.btnUpdate.setOnClickListener {
                    val action =
                        DetailFragmentDirections.actionDetailFragmentToEditWordFragment(wordId)
                    findNavController().navigate(action)
                }

                // Delete → notify + back
                binding.btnDelete.setOnClickListener {
                    showDeleteDialog(wordId)
                }
            }
        }
    }

    private fun showDeleteDialog(wordId: Int) {
        val deleteDialog = DeletePopFragment().apply {
            setListener(object : DeletePopFragment.Listener {
                override fun onClickDelete() {
                    viewModel.deleteWord(wordId)
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
        // Decide which message to show (Unmark vs Done)
        val isUnmark = word.isCompleted

        val confirmDialog = CompletePopFragment().apply {
            setListener(
                object : CompletePopFragment.Listener {
                    override fun onClickYes() {
                        if (word.isCompleted) {
                            // Unmark -> move back to Home list
                            viewModel.markCompleted(word.id, completed = false)
                        } else {
                            // Mark as Done -> move to Completed list
                            viewModel.markCompleted(word.id, completed = true)
                        }
                        setFragmentResult("manage_word", Bundle().apply { putBoolean("refresh", true) })
                        findNavController().popBackStack()
                    }

                    override fun onClickNo() {}
                },
                isUnmark = isUnmark
            )
        }
        confirmDialog.show(parentFragmentManager, "CompletePopFragment")
    }
}
