package com.example.teamdolphin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.Fragment
import kotlin.math.absoluteValue


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
            var projectWidth = findViewById<EditText>(R.id.field_width)
            var projectHeight = findViewById<EditText>(R.id.field_height)
            var darkBackground = findViewById<RadioButton>(R.id.fc_dark_button)
            var backgroundColor: Int
            projectNameString = projectName.text.toString()

            backgroundColor = if(darkBackground.isChecked)
                Color.BLACK
            else
                Color.WHITE

            if(Companion.projectNameIsValid(projectName.text.toString())){
                Toast.makeText(this, "Opening "+projectName.text.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TesterCanvas()::class.java)

                if(projectWidth.text.isNotEmpty()&&projectHeight.text.isNotEmpty()) {
                    intent.putExtra("width", projectWidth.text.toString().toInt().absoluteValue)
                    intent.putExtra("height", projectHeight.text.toString().toInt().absoluteValue)
                }
                intent.putExtra("background", backgroundColor)
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