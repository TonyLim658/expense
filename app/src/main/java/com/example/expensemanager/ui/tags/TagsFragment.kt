package com.example.expensemanager.ui.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.R
import com.example.expensemanager.database.adapter.TagAdapter
import kotlinx.android.synthetic.main.fragment_tags.view.*

class TagsFragment : Fragment() {

    private lateinit var tagViewModel: TagViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        tagViewModel = ViewModelProvider(this).get(TagViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tags, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerview_tag)
        val adapter = this.context?.let { TagAdapter(it, TagAdapter.DeleteTagListener {
            tagId ->  tagViewModel.deleteById(tagId)
        }) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        tagViewModel.allTags.observe(viewLifecycleOwner, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter?.setTags(it) }
        })
        root.fab_add_tag.setOnClickListener {
            val action = TagsFragmentDirections.actionNavigationTagsToNavigationAddTag()
            findNavController().navigate(action)
        }
        return root
    }
}
