package com.team.wordsapp_casestudy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team.wordsapp_casestudy.data.model.Word
import com.team.wordsapp_casestudy.databinding.ItemLayoutWordBinding

class WordsAdapter(
    private var words: List<Word> = emptyList(),
    private val onClick: (Word) -> Unit
) : RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemLayoutWordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount(): Int = words.size

    fun setWords(list: List<Word>) {
        words = list
        notifyDataSetChanged()
    }

    inner class WordViewHolder(
        private val binding: ItemLayoutWordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) {
            binding.tvTitle.text = word.title
            binding.tvMeaning.text = word.meaning
            binding.llWord.setOnClickListener { onClick(word) }
        }
    }
}
