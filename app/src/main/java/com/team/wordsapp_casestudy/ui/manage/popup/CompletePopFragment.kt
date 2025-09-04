package com.team.wordsapp_casestudy.ui.manage.popup

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.team.wordsapp_casestudy.R
class CompletePopFragment : DialogFragment() {

    interface Listener {
        fun onClickYes()
        fun onClickNo()
    }

    private var listener: Listener? = null
    private var isUnmark: Boolean = false  // to change confirmation message in different screen

    fun setListener(listener: Listener, isUnmark: Boolean = false) {
        this.listener = listener
        this.isUnmark = isUnmark
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(requireContext()).inflate(
            R.layout.fragment_complete_pop,
            null
        )

        val btnYes = view.findViewById<MaterialButton>(R.id.mbYes)
        val btnNo = view.findViewById<MaterialButton>(R.id.mbNo)
        val tvMessage = view.findViewById<TextView>(R.id.tvMessage)

        // Change message based on completed or unmark
        tvMessage.text = if (isUnmark) {
            getString(R.string.do_you_want_to_unmark_this_word)
        } else {
            getString(R.string.do_you_want_to_move_this_word_to_completed_list)
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .create()

        btnYes.setOnClickListener {
            listener?.onClickYes()
            dialog.dismiss()
        }

        btnNo.setOnClickListener {
            listener?.onClickNo()
            dialog.dismiss()
        }

        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }
}