package com.example.teamdolphin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlin.math.absoluteValue


class FileCreation : AppCompatActivity() {
    private lateinit var layout: ImageView

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

            var appropriateSize: Boolean = true

            if(projectNameIsValid(projectName.text.toString())){
                val intent = Intent(this, TesterCanvas()::class.java)

                if(projectWidth.text.isNotEmpty()&&projectHeight.text.isNotEmpty()) {
                    intent.putExtra("width", projectWidth.text.toString().toInt().absoluteValue)
                    intent.putExtra("height", projectHeight.text.toString().toInt().absoluteValue)
                    if(projectWidth.text.toString().toInt().absoluteValue<1 ||
                        projectWidth.text.toString().toInt().absoluteValue>2000 ||
                        projectHeight.text.toString().toInt().absoluteValue<1 ||
                        projectHeight.text.toString().toInt().absoluteValue>2000){
                        Toast.makeText(this, "Enter a valid width and height(1-2000)", Toast.LENGTH_SHORT).show()
                        appropriateSize = false
                    }
                }
                intent.putExtra("background", backgroundColor)
                if(appropriateSize){
                    Toast.makeText(this, "Opening "+projectName.text.toString(), Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }
            }
            else{
                Toast.makeText(this, "Enter a valid project name", Toast.LENGTH_SHORT).show()
            }
        }

        val radioGroup = findViewById<View>(R.id.radioGroup) as RadioGroup
        val blackImage = ResourcesCompat.getDrawable(resources, R.drawable.drawable_solid_black, null);
        val whiteImage = ResourcesCompat.getDrawable(resources, R.drawable.drawable_solid_white, null);
        radioGroup.setOnCheckedChangeListener { _, _ ->
            layout = findViewById<ImageView>(R.id.image_preview_container)
            var darkBackground = findViewById<RadioButton>(R.id.fc_dark_button)
            if (darkBackground.isChecked) {
                layout.setImageDrawable(blackImage)
            } else
                layout .setImageDrawable(whiteImage)
        }

        //Button to import image
        //Currenly only shows the image in preview
        //TODO: still need to find a way to apply that image on canvas
        var importButton = findViewById<ImageButton>(R.id.button_import)
        importButton.setOnClickListener {
            Toast.makeText(this, "Import button clicked", Toast.LENGTH_SHORT).show()
            pickImageFromGallery()
        }
    }

    //Creates an temp intent that let's users pick an image
    private fun pickImageFromGallery(){
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    //This method gives access to data provided after user selects an image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== IMAGE_REQUEST_CODE && resultCode== RESULT_OK){
            layout.setImageURI(data?.data)
        }
    }

    companion object {
        /*
                This will test the input provided in EditText Field of Project name
                ...String must not be empty
                 */
        var projectNameString = ""
        var IMAGE_REQUEST_CODE = 100
        fun projectNameIsValid(name: String): Boolean{
            return name.isNotEmpty()
        }
    }
}