package com.example.teamdolphin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
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

        //This is a click listener for the button to navigate to CanvasDrawerPage
        val button = findViewById<Button>(R.id.button_create)
        button.setOnClickListener {
            var projectName = findViewById<EditText>(R.id.field_projectName)
            projectNameString = projectName.text.toString()
            if(Companion.projectNameIsValid(projectName.text.toString())){
                Toast.makeText(this, "Opening "+projectName.text.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TesterCanvas::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Enter a valid project name", Toast.LENGTH_SHORT).show()
            }
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

    companion object {
        /*
                This will test the input provided in EditText Field of Project name
                ...String must not be empty
                 */
        public var projectNameString = ""
        fun projectNameIsValid(name: String): Boolean{
            return name.isNotEmpty()
        }
    }
}