package com.example.bitfit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class HealthCalFragment : Fragment() {

    private val foodsInfo = mutableListOf<Health>()
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var foodsAdapter: HealthAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getFoodItem()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_health_cal, container, false)

        val layoutManager = LinearLayoutManager(context)
        foodsRecyclerView = view.findViewById(R.id.health_item_recycler_view)
        foodsRecyclerView.layoutManager = layoutManager
        foodsRecyclerView.setHasFixedSize(true)
        foodsAdapter = HealthAdapter(view.context, foodsInfo)
        foodsRecyclerView.adapter = foodsAdapter
        return view
    }

    private fun getFoodItem(){
        lifecycleScope.launch {
            (requireActivity().application as HealthApplication).db.healthDao().getAll().collect{ databaseList ->
                databaseList.map { entity ->
                    Health(
                        entity.foodItem,
                        entity.calories
                    )
                }.also{list ->
                    foodsInfo.clear()
                    foodsInfo.addAll(list)
                    foodsAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}