package com.example.expensemanager.ui.trade

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.expensemanager.R
import com.example.expensemanager.database.entity.Tag
import com.example.expensemanager.database.entity.Trade
import kotlinx.android.synthetic.main.fragment_add_trade.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.sql.Date

class AddTradeFragment : Fragment() {

    private lateinit var addTradeViewModel: AddTradeViewModel
    private val args: AddTradeFragmentArgs by navArgs()
    private var date: Date = Date(Calendar.getInstance().time.time)
    private var tag: Tag? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var isIncome = args.isIncome
        addTradeViewModel = ViewModelProvider(this).get(AddTradeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_trade, container, false)
        if (!isIncome) {
            root.spinner_trade_type.setSelection(1)
        }
        val arrayAdapter = ArrayAdapter<Tag>(
            this.context,
            android.R.layout.select_dialog_singlechoice
        )
        val txtDateTrade = root.txt_date_trade
        txtDateTrade.text = SimpleDateFormat("dd/M/yyyy").format(date.time!!)
        val txtTagTrade =  root.txt_tag_trade
        val editTxtAmount = root.edit_txt_add_trade_amount
        val tradeTypeSpinner: Spinner = root.findViewById(R.id.spinner_trade_type)
        root.btn_select_date_trade.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                c.set(year, monthOfYear, dayOfMonth)
                date = Date(c.time.time)
                txtDateTrade.text = SimpleDateFormat("dd/M/yyyy").format(date)
            }, year, month, day)
            dpd.show()
        }
        root.spinner_trade_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                txtTagTrade.text = "Select a tag"
                tag = null
            }
        }
        root.btn_back_home.setOnClickListener {
            val action = AddTradeFragmentDirections.actionNavigationTradeToNavigationHome()
            findNavController().navigate(action)
        }
        root.btn_select_tag.setOnClickListener {
            arrayAdapter.clear()
            addTradeViewModel.getTagsByType(tradeTypeSpinner.selectedItem.toString() == "Income")
                .observe(viewLifecycleOwner, Observer { tags ->
                    tags?.stream()?.forEach { tag ->
                        arrayAdapter.add(tag)
                    }
                })

            val builder: AlertDialog.Builder? = activity?.let {
                AlertDialog.Builder(it)
            }
            builder
                ?.setIcon(R.drawable.ic_loyalty_black_24dp)
                ?.setTitle("Select One Name:")
                ?.setAdapter(
                    arrayAdapter
                ) { dialog, which ->
                    tag = arrayAdapter.getItem(which)
                    val builderInner =
                        AlertDialog.Builder(it.context)
                    builderInner.setMessage(tag?.label)
                    builderInner.setTitle("Your Selected Item is")
                    builderInner.setPositiveButton(
                        "Ok"
                    )
                    { dialog, which ->
                        dialog.dismiss()
                        txtTagTrade.text = tag?.label
                    }
                    builderInner.show()
                }
//                ?.setView(recyclerView)
                    /*
                ?.setPositiveButton("R.string.fire",
                    DialogInterface.OnClickListener { dialog, id ->
                        // FIRE ZE MISSILES!
                    })
                    */
                ?.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            // Create the AlertDialog object and return it
            builder?.create()?.show()

            val builderSingle: AlertDialog.Builder? = activity?.let {
                AlertDialog.Builder(it)
            }
            builderSingle?.setIcon(R.drawable.ic_loyalty_black_24dp)
            builderSingle?.setTitle("Select One Name:")
        }

        root.btn_save_trade2.setOnClickListener {
            val toast = Toast.makeText(it.context, "", Toast.LENGTH_SHORT)
            when {
                tag == null -> {
                    toast.setText("Please select a tag")
                    toast.show()
                    Log.d("TradeFragment", "Tag not selected")
                }
                editTxtAmount.text.toString().isNullOrBlank() -> {
                    toast.setText("Please specify an amount")
                    toast.show()
                    Log.d("TradeFragment", "Amount not specified")
                }
                else -> {
                    val amount: Double = editTxtAmount.text.toString().toDouble()
                    var trade: Trade = Trade(0, amount, tag!!.id, date)
                    addTradeViewModel.insert(trade)
                    val action = AddTradeFragmentDirections.actionNavigationTradeToNavigationHome()
                    findNavController().navigate(action)
                }
            }
        }
        return root
    }

}
