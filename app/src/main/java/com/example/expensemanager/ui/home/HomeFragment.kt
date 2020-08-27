package com.example.expensemanager.ui.home

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.expensemanager.R
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val incomeLayout = root.incomeLayout
        val expenseLayout = root.expenseLayout

        root.btn_add_income.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationTrade(true)
            findNavController().navigate(action)
        }

        root.btn_add_expense.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationTrade(false)
            findNavController().navigate(action)
        }

        root.fab_add_trade.setOnLongClickListener {

            val item = ClipData.Item(it.tag as? CharSequence)

            val dragData = ClipData(
                it.tag as? CharSequence,
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                item)

            val shadowBuilder = View.DragShadowBuilder(it)

            it.startDragAndDrop(
                dragData,   // the data to be dragged
                shadowBuilder,   // the drag shadow builder
                null,       // no need to use local data
                0           // flags (not currently used, set to 0)
            )
        }

        root.fab_add_trade.setOnDragListener{ v, event ->
            // Handles each of the expected events
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    Log.d("DragDrop fab", "STARTED")
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        v.invalidate()
                        v.isVisible = false
                        true
                    } else {
                        false
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    v.invalidate()
                    true
                }

                DragEvent.ACTION_DRAG_LOCATION -> {
                    Log.d("DragDrop fab", "LOCATION")
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    Log.d("DragDrop fab", "EXITED")
                    v.invalidate()
                    true
                }
                DragEvent.ACTION_DROP -> {
                    Log.d("DragDrop fab", "DROP")
                    val item: ClipData.Item = event.clipData.getItemAt(0)
                    v.invalidate()

                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    Log.d("DragDrop fab", "ENDED")
                    v.invalidate()
                    v.isVisible = true
                    true
                }
                else -> {
                    Log.e("DragDrop Example", "Unknown action type received by OnDragListener.")
                    false
                }
            }
        }

        root.expenseLayout.setOnDragListener{ v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    Log.d("DragDrop expense layout", "STARTED")
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        v.invalidate()
                        true
                    } else {
                        false
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    Log.d("DragDrop expense layout", "ENTERED")
                    incomeLayout.alpha = 0.7F
                    v.invalidate()
                    true
                }

                DragEvent.ACTION_DRAG_LOCATION -> {
                    Log.d("DragDrop expense layout", "LOCATION")
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    Log.d("DragDrop expense layout", "EXITED")
                    incomeLayout.alpha = 1F
                    v.invalidate()
                    true
                }
                DragEvent.ACTION_DROP -> {
                    Log.d("DragDrop expense layout", "DROP")
                    val item: ClipData.Item = event.clipData.getItemAt(0)

                    val action = HomeFragmentDirections.actionNavigationHomeToNavigationAddTrade(false)
                    findNavController().navigate(action)
                    v.invalidate()

                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    Log.d("DragDrop expense layout", "ENDED")
                    v.invalidate()
                    true
                }
                else -> {
                    Log.d("DragDrop expense layout", "ELSE ACTION")
                    // An unknown action type was received.
                    Log.e("DragDrop expense layout", "Unknown action type received by OnDragListener.")
                    false
                }
            }
        }

        root.incomeLayout.setOnDragListener{ v, event ->
            // Handles each of the expected events
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    Log.d("DragDrop income layout", "STARTED")
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        v.invalidate()

                        true
                    } else {
                        false
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    Log.d("DragDrop income layout", "ENTERED")
                    expenseLayout.alpha = 0.7F
                    v.invalidate()
                    true
                }

                DragEvent.ACTION_DRAG_LOCATION -> {
                    Log.d("DragDrop income layout", "LOCATION")
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    Log.d("DragDrop income layout", "EXITED")
                    expenseLayout.alpha = 1F
                    v.invalidate()
                    true
                }
                DragEvent.ACTION_DROP -> {
                    Log.d("DragDrop income layout", "DROP")
                    val action = HomeFragmentDirections.actionNavigationHomeToNavigationAddTrade(true)
                    findNavController().navigate(action)
                    v.invalidate()
                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    Log.d("DragDrop income layout", "ENDED")
                    v.invalidate()
                    true
                }
                else -> {
                    // An unknown action type was received.
                    Log.e("DragDrop income layout", "Unknown action type received by OnDragListener.")
                    false
                }
            }
        }
        return root
    }

}