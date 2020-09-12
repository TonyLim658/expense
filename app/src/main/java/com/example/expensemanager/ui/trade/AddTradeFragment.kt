package com.example.expensemanager.ui.trade

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.expensemanager.R
import com.example.expensemanager.database.entity.Tag
import com.example.expensemanager.database.entity.Trade
import kotlinx.android.synthetic.main.fragment_add_trade.view.*
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddTradeFragment : Fragment() {

    private lateinit var addTradeViewModel: AddTradeViewModel
    private val args: AddTradeFragmentArgs by navArgs()
    private var date: Date = Date(Calendar.getInstance().time.time)
    private var tagsChecked: ArrayList<Tag> = arrayListOf()

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
        val txtDateTrade = root.txt_date_trade
        txtDateTrade.text = SimpleDateFormat("dd/M/yyyy").format(date.time!!)
        val txtTagTrade =  root.txt_tag_trade
        val editTxtAmount = root.edit_txt_add_trade_amount
        val editTxtLabel = root.edit_txt_add_trade_label
        val editTxtNote = root.edit_txt_note
        val tradeTypeSpinner: Spinner = root.findViewById(R.id.spinner_trade_type)
        root.btn_select_date_trade.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this.context!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                c.set(year, monthOfYear, dayOfMonth)
                date = Date(c.time.time)
                txtDateTrade.text = SimpleDateFormat("dd/M/yyyy").format(date)
            }, year, month, day)!!
            dpd.show()
        }
        root.spinner_trade_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                txtTagTrade.text = "Select a tag"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                txtTagTrade.text = "Select a tag"
                tagsChecked = arrayListOf()
            }
        }
        root.btn_back_home.setOnClickListener {
            val action = AddTradeFragmentDirections.actionNavigationTradeToNavigationHome()
            findNavController().navigate(action)
        }
        root.btn_select_tag.setOnClickListener {
            txtTagTrade.text = "Select a tag"
            tagsChecked = arrayListOf()
            val tagsTempList: ArrayList<Tag> = ArrayList()
            val tagsLabelList: ArrayList<String> = ArrayList()
            addTradeViewModel.getTagsByType(tradeTypeSpinner.selectedItem.toString() == "Income")
                .observe(viewLifecycleOwner, Observer { tags ->
                    tags?.stream()?.forEach { tag ->
                        tagsTempList.add(tag)
                        tagsLabelList.add(tag.label)
                    }
                    val builder: AlertDialog.Builder? = activity?.let {
                        AlertDialog.Builder(it)
                    }
                    val checkedItem: BooleanArray = BooleanArray(tagsLabelList.size)
                    for(i in tagsLabelList.indices) {
                        checkedItem[i] = false
                    }
                    builder
                        ?.setIcon(R.drawable.ic_loyalty_black_24dp)
                        ?.setTitle("Select One Name:")
                        ?.setMultiChoiceItems(tagsLabelList.toList().toTypedArray(), checkedItem,
                            DialogInterface.OnMultiChoiceClickListener() {
                                    dialogInterface: DialogInterface, i: Int, b: Boolean ->
                            })
                        ?.setPositiveButton(
                            "OK"
                        ) { dialog, which -> // Do something when click positive button
                            Log.d("AddTradeFragment", "Your preferred colors.....")
                            var colors = ""
                            for (i in checkedItem.indices) {
                                val checked: Boolean = checkedItem[i]
                                if (checked) {
                                    if (tagsChecked.isNotEmpty()) {
                                        colors = "$colors, "
                                    }
                                    colors += tagsLabelList[i]
                                    tagsChecked.add(tagsTempList[i])
                                }
                            }
                            if(colors.isNullOrEmpty()) {
                                txtTagTrade.text = "Select a tag"
                            } else {
                                txtTagTrade.text = colors
                            }
                            Log.d("AddTradeFragment", "colors = $colors")
                        }
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
                })
        }

        root.btn_save_trade2.setOnClickListener {
            val toast = Toast.makeText(it.context, "", Toast.LENGTH_SHORT)
            editTxtLabel.background.setTint(
                if (editTxtLabel.text.toString().isNullOrBlank()) Color.RED else Color.BLACK)
            editTxtAmount.background.setTint(
                if (editTxtAmount.text.toString().isNullOrBlank()) Color.RED else Color.BLACK)
            when {
                tagsChecked.isEmpty() -> {
                    toast.setText("Please select a tag")
                    toast.show()
                    Log.i("TradeFragment", "Tag not selected")
                }
                editTxtLabel.text.toString().isNullOrBlank() -> {
                    toast.setText("Please specify a label")
                    toast.show()
                    Log.i("TradeFragment", "Label not specified")
                }
                editTxtAmount.text.toString().isNullOrBlank() -> {
                    toast.setText("Please specify an amount")
                    toast.show()
                    Log.i("TradeFragment", "Amount not specified")
                }
                else -> {
                    val amount: Double = editTxtAmount.text.toString().toDouble()
                    val label: String = editTxtLabel.text.toString().trim()
                    val note: String = editTxtNote.text.toString()
                    var trade = Trade(0, label, note, amount, date)
                    addTradeViewModel.insertWithTags(trade, tagsChecked)
                    val action = AddTradeFragmentDirections.actionNavigationTradeToNavigationHome()
                    findNavController().navigate(action)
                }
            }
        }

        return root
    }

}
