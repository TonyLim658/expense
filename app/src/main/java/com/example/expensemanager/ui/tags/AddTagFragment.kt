package com.example.expensemanager.ui.tags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.R
import com.example.expensemanager.database.entity.Tag
import kotlinx.android.synthetic.main.fragment_add_tag.view.*

class AddTagFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var addTagViewModel: AddTagViewModel
    private lateinit var editTagLabelView: EditText
    private val INCOME_TEXT = "Income"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_tag, container, false)
        addTagViewModel = ViewModelProvider(this).get(AddTagViewModel::class.java)
        editTagLabelView = root.findViewById(R.id.edit_note)
        val tagSpinner: Spinner = root.findViewById(R.id.spinner_tag_type)

        ArrayAdapter.createFromResource(
            this.context!!,
            R.array.tag_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            tagSpinner.adapter = adapter
            tagSpinner.onItemSelectedListener = this
        }
        root.btn_add_tag.setOnClickListener {
            addTagViewModel.getAllSheet().observe(viewLifecycleOwner, Observer { sheet ->
                var IS_INCOME: Boolean = tagSpinner.selectedItem.toString() == INCOME_TEXT
                var t = Tag(0, editTagLabelView.text.toString().trim(), "", IS_INCOME, sheet[0].id)
                addTagViewModel.insert(t)
                val action = AddTagFragmentDirections.actionNavigationAddTagToNavigationTags()
                findNavController().navigate(action)

            })
        }
        return root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val selectedItem: String = parent?.getItemAtPosition(pos).toString()
        Log.d("AddTagFragment", "$selectedItem selected in spinner")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Another interface callback
        Log.d("AddTagFragment", "Nothing selected in spinner")
    }

}