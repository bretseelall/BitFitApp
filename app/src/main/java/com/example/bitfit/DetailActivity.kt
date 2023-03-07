package com.example.bitfit


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val etFoodName = findViewById<EditText>(R.id.editTextFoodName)
        val etFoodCalories = findViewById<EditText>(R.id.editTextNumberCalories)
        val subButton = findViewById<Button>(R.id.submitButton)

        subButton.setOnClickListener{
            lifecycleScope.launch(IO){
                (application as HealthApplication).db.healthDao().insert(
                    HealthEntity(
                        foodItem = etFoodName.text.toString(),
                        calories = etFoodCalories.text.toString().toInt()
                    )
                )
            }
            this.finish()
        }
    }
}