package com.team.wordsapp_casestudy.ui.manage.popup

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.team.wordsapp_casestudy.R

class SortPopFragment : DialogFragment() {

    interface Listener {
       fun onClickDone()
        fun onSortOrderSelected(isAscending: Boolean)
        fun onSortBySelected(isTitle: Boolean)
    }

    private var listener: SortPopFragment.Listener? = null

    fun setListener(listener: SortPopFragment.Listener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(requireContext())
            .inflate(
                R.layout.fragment_sort_pop,
                null
            )

        val btnDone = view.findViewById<MaterialButton>(R.id.mbDone)
        val sortOrder = view.findViewById<RadioGroup>(R.id.rgSortOrder)
        val sortBy = view.findViewById<RadioGroup>(R.id.rgSortBy)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .create()

        btnDone.setOnClickListener {
            listener?.onClickDone()
            dialog.dismiss()
        }

        sortOrder.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.rbAscend -> listener?.onSortOrderSelected(true)
                R.id.rbDescend -> listener?.onSortOrderSelected(false)
            }
        }

        sortBy.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.rbTitle -> listener?.onSortBySelected(true)
                R.id.rbDate -> listener?.onSortBySelected(false)
            }
        }

        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }

}