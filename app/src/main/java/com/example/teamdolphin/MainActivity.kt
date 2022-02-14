package com.example.teamdolphin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
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

            val intent = Intent(this, TesterCanvas()::class.java)
            intent.putExtra("imagePath", path)
            startActivity(intent)

        }

        gridView.setOnItemLongClickListener { _, _, i, _ ->
            //Toast.makeText(this, "Opening:", Toast.LENGTH_SHORT).show()
            var name = fileNames[i]
            var path = filePaths[i]
            val dialogClickListener =
                DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {deleteProject(path, name)}
                        DialogInterface.BUTTON_NEGATIVE -> {}
                    }
                }
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to delete $name?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()

            return@setOnItemLongClickListener true
        }

    }

    private fun deleteProject(path: String, name: String){
        val file = File(path)
        if(file.exists()){
            val result = file.delete()
            if (result) {
                Toast.makeText(this, "Deleted $name", Toast.LENGTH_SHORT).show()
                listImages()
            } else {
                Toast.makeText(this, "An Error occurred while deleting the project!", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "File does not exist.", Toast.LENGTH_SHORT).show()

        }
    }
}