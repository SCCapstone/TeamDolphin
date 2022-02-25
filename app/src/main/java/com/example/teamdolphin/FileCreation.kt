package com.example.teamdolphin

import android.content.ContentUris
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlin.math.absoluteValue


class FileCreation : AppCompatActivity() {
    private lateinit var layout: ImageView
    private lateinit var importedImagePath: String

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

            var appropriateSize = true

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
        layout = findViewById(R.id.image_preview_container)

        radioGroup.setOnCheckedChangeListener { _, _ ->
            var darkBackground = findViewById<RadioButton>(R.id.fc_dark_button)
            if (darkBackground.isChecked) {
                layout.setImageDrawable(blackImage)
            } else
                layout .setImageDrawable(whiteImage)
        }

        //Button to import image
        //Currently only shows the image in preview
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
            importedImagePath = getPath(data)!!
            println("Image Bitmap: $importedImagePath")
            layout.setImageURI(data?.data)

            var projectWidth = findViewById<EditText>(R.id.field_width)
            var projectHeight = findViewById<EditText>(R.id.field_height)
            var darkToggleButton = findViewById<RadioButton>(R.id.fc_dark_button)
            var lightToggleButton = findViewById<RadioButton>(R.id.fc_light_button)

            //Disabling things that won't be needed if user imports an image
            projectHeight.isEnabled = false
            projectHeight.setText("Locked")
            projectWidth.isEnabled = false
            projectWidth.setText("Locked")
            darkToggleButton.isEnabled = false
            darkToggleButton.isChecked = false
            lightToggleButton.isEnabled = false
            lightToggleButton.isChecked = false

            val button = findViewById<Button>(R.id.button_create)
            button.setOnClickListener {
                var projectName = findViewById<EditText>(R.id.field_projectName)
                if(projectNameIsValid(projectName.text.toString())) {
                    val intent = Intent(this, TesterCanvas()::class.java)
                    intent.putExtra("imagePath", importedImagePath)
                    startActivity(intent)
                }
                Toast.makeText(this, "Enter a valid project name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //This article helped in getting this functionality working
    //https://camposha.info/android-examples/android-capture-pick-image/#gsc.tab=0
    private fun getImagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = contentResolver.query(uri!!, null, selection, null, null )
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    private fun getPath(data: Intent?): String? {
        var imagePath: String? = null
        val uri = data!!.data
        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(this, uri)){
            val docId = DocumentsContract.getDocumentId(uri)
            if (uri != null) {
                if ("com.android.providers.media.documents" == uri.authority){
                    val id = docId.split(":")[1]
                    val selection = MediaStore.Images.Media._ID + "=" + id
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        selection)
                } else if (uri != null) {
                    if ("com.android.providers.downloads.documents" == uri.authority){
                        val contentUri = ContentUris.withAppendedId(Uri.parse(
                            "content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                        imagePath = getImagePath(contentUri, null)
                    }
                }
            }
        }
        else if (uri != null) {
            if ("content".equals(uri.scheme, ignoreCase = true)){
                imagePath = getImagePath(uri, null)
            }
            else if (uri != null) {
                if ("file".equals(uri.scheme, ignoreCase = true)){
                    imagePath = uri.path
                }
            }
        }
        return imagePath
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