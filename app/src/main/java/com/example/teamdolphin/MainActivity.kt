package com.example.teamdolphin

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.jar.Manifest
import android.widget.GridView




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

    private fun listImages() {
        var cols = listOf<String>(MediaStore.Images.Thumbnails.DATA).toTypedArray()
        rs = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,  cols,null,null, null)!!
        if(rs?.moveToNext()!!)
            Toast.makeText(applicationContext, rs?.getString(0), Toast.LENGTH_LONG).show()
        var gridView: GridView = findViewById(R.id.homepage_gridView)
        gridView.adapter = ImageAdapter(applicationContext)
        print("12345")
    }


    //Adapter that helps inflate a view
    inner class ImageAdapter : BaseAdapter{
        lateinit var context: Context

        constructor(context: Context){
            this.context = context
        }

        override fun getCount(): Int {
            return rs.count
        }

        override fun getItem(p0: Int): Any {
            return p0
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var iv = ImageView(context)
            rs.moveToPosition(p0)
            var path = rs.getString(0)
            var bitmap = BitmapFactory.decodeFile(path)
            iv.setImageBitmap(bitmap)
            iv.setPadding(20, 20, 20, 20)
            iv.layoutParams = AbsListView.LayoutParams(GridView.AUTO_FIT, 500)
            return iv
        }

    }
}