package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val foodsInfo = mutableListOf<Health>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterButton = findViewById<Button>(R.id.enterItemButton)
        val rvHealth = findViewById<RecyclerView>(R.id.healthList)
        val adapter = HealthAdapter(this, foodsInfo)

        lifecycleScope.launch {
            (application as HealthApplication).db.healthDao().getAll().collect{ databaseList ->
                databaseList.map { entity ->
                    Health(
                        entity.foodItem,
                        entity.calories
                    )
                }.also{list ->
                    foodsInfo.clear()
                    foodsInfo.addAll(list)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        rvHealth.adapter = adapter
        rvHealth.layoutManager = LinearLayoutManager(this)

        enterButton.setOnClickListener{
            val intent = Intent(this@MainActivity,DetailActivity::class.java)
            startActivity(intent)
        }


    }
}