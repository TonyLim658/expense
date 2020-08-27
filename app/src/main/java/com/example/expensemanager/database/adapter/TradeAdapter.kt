package com.example.expensemanager.database.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.R
import com.example.expensemanager.database.entity.Trade
import com.example.expensemanager.database.entity.TradeWithTag
import java.text.SimpleDateFormat

class TradeAdapter internal constructor(
    context: Context, val clickListener: TradeAdapter.DeleteTradeListener
) : RecyclerView.Adapter<TradeAdapter.tradeViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var trades = emptyList<TradeWithTag>()

    inner class tradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tradeItemView: TextView = itemView.findViewById(R.id.textView)
        val tradeDeleteButton: ImageButton = itemView.findViewById(R.id.btn_delete_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tradeViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return tradeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: tradeViewHolder, position: Int) {
        val current = trades[position]
        holder.tradeItemView.text = current.tradeLabel + " " + current.amount.toString() + "€ " +
                current.tagLabel + " " +
                SimpleDateFormat("dd/M").format(current.date.time!!)
        holder.tradeDeleteButton.setOnClickListener {
            Log.d("tradeListAdapter : ", "delete me on position $position !")
            var newtrades = this.trades.toMutableList()
            var tradeToDelete = this.trades[position]
            clickListener.onClick(tradeToDelete!!)
            newtrades.removeAt(position)
            Log.d("tradeListAdapter : ", newtrades.toString())
            this.trades = newtrades
            notifyDataSetChanged()
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, trades.size);
        }
    }

    internal fun setTrades(trades: List<TradeWithTag>) {
        this.trades = trades
        notifyDataSetChanged()
    }

    override fun getItemCount() = trades.size

    class DeleteTradeListener(val clickListener: (tradeId: Long) -> Unit) {
        fun onClick(trade: TradeWithTag) = clickListener(trade.id)
    }
}