package com.example.expensemanager.database.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.R
import com.example.expensemanager.database.entity.Tag

class TagAdapter internal constructor(
    context: Context, val clickListener: DeleteTagListener
) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tags = emptyList<Tag>() // Cached copy of words

    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: ConstraintLayout = itemView.findViewById(R.id.linearLayoutTagRecycler)
        val tagItemView: TextView = itemView.findViewById(R.id.textView)
        val tagDeleteButton: ImageButton = itemView.findViewById(R.id.btn_delete_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return TagViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val current = tags[position]
        holder.tagItemView.text = current.label
        holder.layout.setBackgroundColor(if(position % 2 == 1) Color.LTGRAY else Color.WHITE)
        holder.tagItemView.setTextColor(if(current.is_income)
            Color.parseColor("#0F9D58") else Color.parseColor("#DB4437"))

        holder.tagDeleteButton.setOnClickListener {
            Log.d("TagListAdapter : ", "delete me on position $position !")
            var newTags = this.tags.toMutableList()
            var tagToDelete = this.tags[position]
            clickListener.onClick(tagToDelete!!)
            newTags.removeAt(position)
            Log.d("TagListAdapter : ", newTags.toString())
            this.tags = newTags
            notifyDataSetChanged()
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tags.size);
        }
    }

    internal fun setTags(tags: List<Tag>) {
        this.tags = tags
        notifyDataSetChanged()
    }

    override fun getItemCount() = tags.size

    class DeleteTagListener(val clickListener: (tagId: Long) -> Unit) {
        fun onClick(tag: Tag) = clickListener(tag.id)
    }
}