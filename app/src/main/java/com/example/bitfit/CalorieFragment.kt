package com.example.bitfit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

var counter = 0
var totalCalories = 0
var min = 0
var max = 0
class CalorieFragment : Fragment() {
    private val foodsInfo = mutableListOf<Health>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calorie, container, false)
        var aveCal = view.findViewById<TextView>(R.id.tvAveCal)
        val maxCal = view.findViewById<TextView>(R.id.tvMaxCal)
        val minCal = view.findViewById<TextView>(R.id.tvMinCal)
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
                    while(counter<foodsInfo.size){
                        totalCalories += foodsInfo[counter].calories
                        if(counter == 0)
                        {
                            min = foodsInfo[counter].calories
                            max = foodsInfo[counter].calories
                        }
                        else if(foodsInfo[counter].calories < min)
                            min = foodsInfo[counter].calories
                        else if(foodsInfo[counter].calories > max)
                            max = foodsInfo[counter].calories
                        counter++
                    }
                    aveCal.text = (totalCalories / foodsInfo.size).toString()
                    minCal.text = min.toString()
                    maxCal.text = max.toString()
                }
            }
        }
        return view

    }

}