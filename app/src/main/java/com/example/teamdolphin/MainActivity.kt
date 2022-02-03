package com.example.teamdolphin

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.os.Environment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File

//This is the Home Page
class MainActivity : AppCompatActivity() {
    lateinit var rs:Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This is a click listener for the button to navigate to FileCreation Page
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, FileCreation::class.java)
            startActivity(intent)
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, Array(1){android.Manifest.permission.READ_EXTERNAL_STORAGE}, 121)
        }
        listImages()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==121 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            listImages()
    }

    private fun listImages(){
        var fileNames = mutableListOf<String>()
        var filePaths = mutableListOf<String>()
        var allFiles = emptyArray<File>()

        //https://stackoverflow.com/questions/47726495/how-to-get-all-images-from-specific-folder/47726525
        val folder = File(Environment.getExternalStorageDirectory().toString() + "/Pictures/DolphinArt Projects")
        if (folder.exists()) {
            //println("FolderLocation: "+folder.path)
            allFiles = folder.listFiles { _, name ->
                name.endsWith(".png")
            }!!
        }

        for (file in allFiles){
            fileNames.add(file.path.takeLastWhile{ it != '/' })
            filePaths.add(file.path)
            //println("FilePath: "+ file.path)
            //println("FileName: "+ file.path.takeLastWhile{ it != '/' })
        }

        var gridView: GridView = findViewById(R.id.homepage_gridView)
        gridView.adapter = ProjectAdapter(applicationContext, fileNames.toTypedArray(), filePaths.toTypedArray())

        gridView.setOnItemClickListener{
                _, _, i, _ ->
            var path = filePaths[i]
            Toast.makeText(this, "$path clicked", Toast.LENGTH_SHORT).show()
            var projectName = path.takeLastWhile{ it != '/' }
            Toast.makeText(this, "Opening: $projectName", Toast.LENGTH_SHORT).show()
            //Put code for opening file in canvas here

            projectName = projectName.takeWhile{ it != '.' }
            FileCreation.projectNameString = projectName

            val intent = Intent(this, TesterCanvas::class.java)
            startActivity(intent)
        }
    }
}