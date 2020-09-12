package com.expense.expenseanalyst.database.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.expense.expenseanalyst.R
import com.expense.expenseanalyst.database.entity.Trade
import java.text.SimpleDateFormat

class TradeAdapter internal constructor(
    context: Context, val clickListener: TradeAdapter.DeleteTradeListener
) : RecyclerView.Adapter<TradeAdapter.tradeViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var trades = emptyList<Trade>()

    inner class tradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: ConstraintLayout = itemView.findViewById(R.id.linearLayoutTagRecycler)
        val tradeItemView: TextView = itemView.findViewById(R.id.textView)
        val tradeDeleteButton: ImageButton = itemView.findViewById(R.id.btn_delete_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tradeViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return tradeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: tradeViewHolder, position: Int) {
        val current = trades[position]
        holder.layout.setBackgroundColor(if(position % 2 == 1) Color.LTGRAY else Color.WHITE)
        holder.tradeItemView.text = current.label + " " + current.amount.toString() +
                "€ " + SimpleDateFormat("dd/M").format(current.date.time!!)
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

    internal fun setTrades(trades: List<Trade>) {
        this.trades = trades
        notifyDataSetChanged()
    }

    override fun getItemCount() = trades.size

    class DeleteTradeListener(val clickListener: (tradeId: Long) -> Unit) {
        fun onClick(trade: Trade) = clickListener(trade.id)
    }
}