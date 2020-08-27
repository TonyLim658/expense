package com.example.expensemanager.ui.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.R
import com.example.expensemanager.database.adapter.TradeAdapter

class TradeFragment : Fragment() {

    private lateinit var tradeViewModel: TradeViewModel
    private val args: TradeFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var isIncome = args.isIncome
        tradeViewModel = ViewModelProvider(this).get(TradeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_trades, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerview_tag)
        val adapter = this.context?.let { TradeAdapter(it, TradeAdapter.DeleteTradeListener {
            tradeId ->  tradeViewModel.deleteById(tradeId)
        }) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        tradeViewModel.getTradesWithTagByType(isIncome).observe(viewLifecycleOwner, Observer { words ->
            words?.let { adapter?.setTrades(it) }
        })
        return root
    }
}
