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
import com.example.expensemanager.database.entity.Tag

class TagAdapter internal constructor(
    context: Context, val clickListener: DeleteTagListener
) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tags = emptyList<Tag>() // Cached copy of words

    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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