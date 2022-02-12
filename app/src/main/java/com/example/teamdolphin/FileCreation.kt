package com.example.teamdolphin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import java.security.AccessController.getContext
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
            projectNameString = projectName.text.toString()

            var backgroundColor: Int = if(darkBackground.isChecked)
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

        val radioGroup = findViewById<View>(R.id.radioGroup) as RadioGroup
        radioGroup.setOnCheckedChangeListener { _, _ ->
            var layout = findViewById<FragmentContainerView>(R.id.image_preview_container)
            var darkBackground = findViewById<RadioButton>(R.id.fc_dark_button)
            if (darkBackground.isChecked) {
                layout.setBackgroundColor(Color.BLACK)
            } else
                layout.setBackgroundColor(Color.WHITE)
        }
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