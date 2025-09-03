package com.team.wordsapp_casestudy.ui.manage.popup

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.team.wordsapp_casestudy.R

class DeletePopFragment : DialogFragment() {

    interface Listener {
        fun onClickConfirm()

        fun onClickDelete()
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(requireContext())
            .inflate(
                R.layout.fragment_delete_pop,
                null
            )

        val btnCancel = view.findViewById<MaterialButton>(R.id.mbCancel)
        val btnDelete = view.findViewById<MaterialButton>(R.id.mbDelete)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .create()

        btnCancel.setOnClickListener {
            listener?.onClickConfirm()
            dialog.dismiss()
        }

        btnDelete.setOnClickListener {
            listener?.onClickDelete()
            dialog.dismiss()
        }

        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }
}