package com.example.teamdolphin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
//This is the Home Page
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This is a click listener for the button to navigate to FileCreation Page
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, FileCreation::class.java)
            startActivity(intent)
        }
    }
}