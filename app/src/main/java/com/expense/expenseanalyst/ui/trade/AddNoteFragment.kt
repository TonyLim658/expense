package com.expense.expenseanalyst.ui.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.expense.expenseanalyst.R
import kotlinx.android.synthetic.main.fragment_add_note.view.*

class AddNoteFragment : Fragment() {

    private lateinit var addNoteViewModel: AddNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_note, container, false)
        addNoteViewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)

        root.btn_update_note.setOnClickListener {
        }
        return root
    }

}