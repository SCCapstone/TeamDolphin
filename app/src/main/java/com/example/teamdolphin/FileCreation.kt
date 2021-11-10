package com.example.teamdolphin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class FileCreation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_creation)

        //This is a click listener for cancel button to navigate back to the homepage
        val imageButton = findViewById<ImageButton>(R.id.cancel_button)
        imageButton.setOnClickListener {
            super.onBackPressed()
        }
    }

    /*
        This method takes in a fragment and adds it to a container view
     */
    private fun addFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.appbar_container, fragment)
        fragmentTransaction.commit()
    }
}