package com.expense.expenseanalyst.ui.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.expense.expenseanalyst.R

class DataFragment : Fragment() {

    private lateinit var dataViewModel: DataViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dataViewModel =
                ViewModelProviders.of(this).get(DataViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_data, container, false)
        val textView: TextView = root.findViewById(R.id.text_trade)
        dataViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
