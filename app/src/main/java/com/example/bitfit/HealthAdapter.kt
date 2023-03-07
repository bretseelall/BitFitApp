package com.example.bitfit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HealthAdapter(private val context: Context, private val foodItem: List<Health>): RecyclerView.Adapter<HealthAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       val food: TextView = itemView.findViewById(R.id.foodName)
        val calories: TextView = itemView.findViewById(R.id.foodCalories)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.health_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foods = foodItem[position]

        holder.food.text = foods.food
        holder.calories.text = foods.calories.toString()

    }
}